package com.example.examen2.materia.service;

import com.example.examen2.materia.model.Semestre;
import com.example.examen2.materia.model.SemestreDTO;
import com.example.examen2.materia.repository.SemestreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SemestreService {

    @Autowired
    private SemestreRepository semestreRepository;

    public List<SemestreDTO> findAll() {
        return semestreRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public SemestreDTO findById(Long id) {
        Semestre semestre = semestreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Semestre not found with id: " + id));
        return mapEntityToDto(semestre);
    }

    public SemestreDTO createSemestre(SemestreDTO semestreDTO) {
        Semestre semestre = mapDtoToEntity(semestreDTO);
        return mapEntityToDto(semestreRepository.save(semestre));
    }

    public SemestreDTO updateSemestre(Long id, SemestreDTO semestreDTO) {
        Semestre existingSemestre = semestreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Semestre not found with id: " + id));
        existingSemestre.setPeriodo(semestreDTO.getPeriodo());
        existingSemestre.setAnio(semestreDTO.getAnio());
        return mapEntityToDto(semestreRepository.save(existingSemestre));
    }

    public void deleteSemestre(Long id) {
        Semestre semestre = semestreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Semestre not found with id: " + id));
        semestreRepository.delete(semestre);
    }

    private Semestre mapDtoToEntity(SemestreDTO dto) {
        Semestre semestre = new Semestre();
        semestre.setPeriodo(dto.getPeriodo());
        semestre.setAnio(dto.getAnio());
        return semestre;
    }

    private SemestreDTO mapEntityToDto(Semestre semestre) {
        SemestreDTO dto = new SemestreDTO();
        dto.setId(semestre.getId());
        dto.setPeriodo(semestre.getPeriodo());
        dto.setAnio(semestre.getAnio());
        return dto;
    }
}
