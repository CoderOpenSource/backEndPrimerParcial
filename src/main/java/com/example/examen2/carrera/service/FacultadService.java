package com.example.examen2.carrera.service;

import com.example.examen2.carrera.model.FacultadDTO;
import com.example.examen2.carrera.model.Facultad;
import com.example.examen2.carrera.model.Carrera;
import com.example.examen2.carrera.repository.FacultadRepository;
import com.example.examen2.carrera.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FacultadService {

    @Autowired
    private FacultadRepository facultadRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    public List<Facultad> findAll() {
        return facultadRepository.findAll();
    }

    @Transactional
    public Facultad createFacultad(FacultadDTO facultadDTO) {
        Facultad facultad = mapDtoToEntity(facultadDTO);
        return facultadRepository.save(facultad);
    }

    @Transactional
    public Facultad updateFacultad(Long id, FacultadDTO facultadDTO) {
        Facultad existingFacultad = facultadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Facultad not found with id: " + id));
        updateEntityFromDto(existingFacultad, facultadDTO);
        return facultadRepository.save(existingFacultad);
    }

    public void deleteFacultad(Long id) {
        Facultad facultad = facultadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Facultad not found with id: " + id));
        facultadRepository.delete(facultad);
    }

    private Facultad mapDtoToEntity(FacultadDTO dto) {
        Facultad facultad = new Facultad();
        facultad.setNombre(dto.getNombre());

        if (dto.getCarreraIds() != null) {
            Set<Carrera> carreras = dto.getCarreraIds().stream()
                    .map(carreraRepository::findById)
                    .map(optional -> optional.orElseThrow(() -> new IllegalArgumentException("Invalid carreraId")))
                    .collect(Collectors.toSet());
            facultad.setCarreras(carreras);
        }

        return facultad;
    }

    private void updateEntityFromDto(Facultad facultad, FacultadDTO dto) {
        facultad.setNombre(dto.getNombre());

        if (dto.getCarreraIds() != null) {
            Set<Carrera> carreras = dto.getCarreraIds().stream()
                    .map(carreraRepository::findById)
                    .map(optional -> optional.orElseThrow(() -> new IllegalArgumentException("Invalid carreraId")))
                    .collect(Collectors.toSet());
            facultad.setCarreras(carreras);
        }
    }
}
