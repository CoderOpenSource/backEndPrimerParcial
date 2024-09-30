package com.example.examen2.materia.service;

import com.example.examen2.materia.model.Materia;
import com.example.examen2.materia.model.MateriaDTO;
import com.example.examen2.materia.repository.MateriaRepository;
import com.example.examen2.carrera.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    public List<MateriaDTO> getAllMaterias() {
        return materiaRepository.findAll().stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Transactional
    public MateriaDTO createMateria(MateriaDTO materiaDTO) {
        Materia materia = mapDtoToEntity(materiaDTO);
        Materia savedMateria = materiaRepository.save(materia);
        return mapEntityToDto(savedMateria);
    }

    @Transactional
    public MateriaDTO updateMateria(Long id, MateriaDTO materiaDTO) {
        Materia existingMateria = materiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Materia not found with id: " + id));
        updateEntityFromDto(existingMateria, materiaDTO);
        Materia updatedMateria = materiaRepository.save(existingMateria);
        return mapEntityToDto(updatedMateria);
    }

    @Transactional
    public void deleteMateria(Long id) {
        Materia existingMateria = materiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Materia not found with id: " + id));
        materiaRepository.delete(existingMateria);
    }

    private Materia mapDtoToEntity(MateriaDTO dto) {
        Materia materia = new Materia();
        materia.setNombre(dto.getNombre());
        materia.setCreditos(dto.getCreditos());
        materia.setCarrera(dto.getCarrera());
        return materia;
    }

    private void updateEntityFromDto(Materia materia, MateriaDTO dto) {
        materia.setNombre(dto.getNombre());
        materia.setCreditos(dto.getCreditos());
        materia.setCarrera(dto.getCarrera());
    }

    private MateriaDTO mapEntityToDto(Materia materia) {
        MateriaDTO dto = new MateriaDTO();
        dto.setId(materia.getId());
        dto.setNombre(materia.getNombre());
        dto.setCreditos(materia.getCreditos());
        dto.setCarrera(materia.getCarrera());
        return dto;
    }
}
