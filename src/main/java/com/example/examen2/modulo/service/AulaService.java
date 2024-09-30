package com.example.examen2.modulo.service;

import com.example.examen2.modulo.model.AulaDTO;
import com.example.examen2.modulo.model.Aula;
import com.example.examen2.carrera.repository.FacultadRepository;
import com.example.examen2.modulo.repository.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private FacultadRepository facultadRepository;

    public List<AulaDTO> findAll() {
        return aulaRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public AulaDTO createAula(AulaDTO aulaDTO) {
        Aula aula = mapDtoToEntity(aulaDTO);
        Aula savedAula = aulaRepository.save(aula);
        return mapEntityToDto(savedAula);
    }

    public AulaDTO updateAula(Long id, AulaDTO aulaDTO) {
        Aula aula = aulaRepository.findById(id).orElseThrow(() -> new RuntimeException("Aula not found"));
        aula.setNombre(aulaDTO.getNombre());
        aula.setLatitud(aulaDTO.getLatitud());
        aula.setLongitud(aulaDTO.getLongitud());
        aula.setFacultad(facultadRepository.findById(aulaDTO.getFacultadId()).orElse(null));
        Aula updatedAula = aulaRepository.save(aula);
        return mapEntityToDto(updatedAula);
    }

    public void deleteAula(Long id) {
        aulaRepository.deleteById(id);
    }

    private AulaDTO mapEntityToDto(Aula aula) {
        AulaDTO dto = new AulaDTO();
        dto.setId(aula.getId());
        dto.setNombre(aula.getNombre());
        dto.setLatitud(aula.getLatitud());
        dto.setLongitud(aula.getLongitud());
        dto.setFacultadId(aula.getFacultad() != null ? aula.getFacultad().getId() : null);
        return dto;
    }

    private Aula mapDtoToEntity(AulaDTO aulaDTO) {
        Aula aula = new Aula();
        aula.setId(aulaDTO.getId());
        aula.setNombre(aulaDTO.getNombre());
        aula.setLatitud(aulaDTO.getLatitud());
        aula.setLongitud(aulaDTO.getLongitud());
        aula.setFacultad(facultadRepository.findById(aulaDTO.getFacultadId()).orElse(null));
        return aula;
    }
}
