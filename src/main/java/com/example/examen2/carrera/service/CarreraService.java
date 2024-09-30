package com.example.examen2.carrera.service;

import com.example.examen2.carrera.model.Carrera;
import com.example.examen2.carrera.model.CarreraDTO;
import com.example.examen2.carrera.model.FacultadDTO;
import com.example.examen2.carrera.repository.CarreraRepository;
import com.example.examen2.carrera.repository.FacultadRepository;
import com.example.examen2.materia.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarreraService {

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private FacultadRepository facultadRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    public List<CarreraDTO> findAll() {
        return carreraRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public CarreraDTO findById(Long id) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Carrera not found with id: " + id));
        return mapEntityToDto(carrera);
    }

    @Transactional
    public CarreraDTO createCarrera(CarreraDTO carreraDTO) {
        Carrera carrera = mapDtoToEntity(carreraDTO);
        return mapEntityToDto(carreraRepository.save(carrera));
    }

    @Transactional
    public CarreraDTO updateCarrera(Long id, CarreraDTO carreraDTO) {
        Carrera existingCarrera = carreraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Carrera not found with id: " + id));
        updateEntityFromDto(existingCarrera, carreraDTO);
        return mapEntityToDto(carreraRepository.save(existingCarrera));
    }

    @Transactional
    public void deleteCarrera(Long id) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Carrera not found with id: " + id));
        carreraRepository.delete(carrera);
    }

    private Carrera mapDtoToEntity(CarreraDTO dto) {
        Carrera carrera = new Carrera();
        carrera.setNombre(dto.getNombre());
        carrera.setCodigo(dto.getCodigo());
        if (dto.getFacultad() != null && dto.getFacultad().getId() != null) {
            carrera.setFacultad(facultadRepository.findById(dto.getFacultad().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid facultadId: " + dto.getFacultad().getId())));
        }
        if (dto.getMateriaIds() != null) {
            carrera.setMaterias(dto.getMateriaIds().stream()
                    .map(id -> materiaRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid materiaId: " + id)))
                    .collect(Collectors.toSet()));
        }
        return carrera;
    }

    private void updateEntityFromDto(Carrera carrera, CarreraDTO dto) {
        carrera.setNombre(dto.getNombre());
        carrera.setCodigo(dto.getCodigo());
        if (dto.getFacultad() != null && dto.getFacultad().getId() != null) {
            carrera.setFacultad(facultadRepository.findById(dto.getFacultad().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid facultadId: " + dto.getFacultad().getId())));
        }
        if (dto.getMateriaIds() != null) {
            carrera.setMaterias(dto.getMateriaIds().stream()
                    .map(id -> materiaRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid materiaId: " + id)))
                    .collect(Collectors.toSet()));
        }
    }

    private CarreraDTO mapEntityToDto(Carrera carrera) {
        CarreraDTO dto = new CarreraDTO();
        dto.setId(carrera.getId());
        dto.setNombre(carrera.getNombre());
        dto.setCodigo(carrera.getCodigo());
        if (carrera.getFacultad() != null) {
            FacultadDTO facultadDTO = new FacultadDTO();
            facultadDTO.setId(carrera.getFacultad().getId());
            facultadDTO.setNombre(carrera.getFacultad().getNombre());
            facultadDTO.setCarreraIds(carrera.getFacultad().getCarreras().stream()
                    .map(Carrera::getId)
                    .collect(Collectors.toSet()));
            dto.setFacultad(facultadDTO);
        }
        if (carrera.getMaterias() != null) {
            dto.setMateriaIds(carrera.getMaterias().stream()
                    .map(materia -> materia.getId())
                    .collect(Collectors.toSet()));
        }
        return dto;
    }
}
