package com.example.examen2.programacionacademica.service;

import com.example.examen2.programacionacademica.model.SesionClase;
import com.example.examen2.programacionacademica.model.SesionClaseDTO;
import com.example.examen2.programacionacademica.model.ProgramacionAcademica;
import com.example.examen2.programacionacademica.repository.SesionClaseRepository;
import com.example.examen2.programacionacademica.repository.ProgramacionAcademicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SesionClaseService {

    @Autowired
    private SesionClaseRepository sesionClaseRepository;

    @Autowired
    private ProgramacionAcademicaRepository programacionAcademicaRepository;

    public List<SesionClaseDTO> findAll() {
        return sesionClaseRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public SesionClaseDTO findById(Long id) {
        SesionClase sesionClase = sesionClaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("SesionClase not found with id: " + id));
        return mapEntityToDto(sesionClase);
    }

    @Transactional
    public SesionClaseDTO createSesionClase(SesionClaseDTO sesionClaseDTO) {
        SesionClase sesionClase = mapDtoToEntity(sesionClaseDTO);
        return mapEntityToDto(sesionClaseRepository.save(sesionClase));
    }

    @Transactional
    public SesionClaseDTO updateSesionClase(Long id, SesionClaseDTO sesionClaseDTO) {
        SesionClase existingSesionClase = sesionClaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("SesionClase not found with id: " + id));
        updateEntityFromDto(existingSesionClase, sesionClaseDTO);
        return mapEntityToDto(sesionClaseRepository.save(existingSesionClase));
    }

    @Transactional
    public void deleteSesionClase(Long id) {
        SesionClase sesionClase = sesionClaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("SesionClase not found with id: " + id));

        // Eliminar las relaciones con ProgramacionAcademica
        for (ProgramacionAcademica programacionAcademica : sesionClase.getProgramacionesAcademicas()) {
            programacionAcademica.getSesiones().remove(sesionClase);
            programacionAcademicaRepository.save(programacionAcademica);
        }

        sesionClase.getProgramacionesAcademicas().clear();
        sesionClaseRepository.save(sesionClase);

        // Ahora podemos eliminar la SesionClase
        sesionClaseRepository.delete(sesionClase);
    }

    private SesionClase mapDtoToEntity(SesionClaseDTO dto) {
        SesionClase sesionClase = new SesionClase();
        sesionClase.setDiaSemana(dto.getDiaSemana());
        sesionClase.setHoraInicio(dto.getHoraInicio());
        sesionClase.setHoraFin(dto.getHoraFin());
        if (dto.getProgramacionAcademicaIds() != null && !dto.getProgramacionAcademicaIds().isEmpty()) {
            Set<ProgramacionAcademica> programacionesAcademicas = programacionAcademicaRepository.findAllById(dto.getProgramacionAcademicaIds()).stream().collect(Collectors.toSet());
            sesionClase.setProgramacionesAcademicas(programacionesAcademicas);
        }
        return sesionClase;
    }

    private void updateEntityFromDto(SesionClase sesionClase, SesionClaseDTO dto) {
        sesionClase.setDiaSemana(dto.getDiaSemana());
        sesionClase.setHoraInicio(dto.getHoraInicio());
        sesionClase.setHoraFin(dto.getHoraFin());
        if (dto.getProgramacionAcademicaIds() != null && !dto.getProgramacionAcademicaIds().isEmpty()) {
            Set<ProgramacionAcademica> programacionesAcademicas = programacionAcademicaRepository.findAllById(dto.getProgramacionAcademicaIds()).stream().collect(Collectors.toSet());
            sesionClase.setProgramacionesAcademicas(programacionesAcademicas);
        }
    }

    private SesionClaseDTO mapEntityToDto(SesionClase sesionClase) {
        SesionClaseDTO dto = new SesionClaseDTO();
        dto.setId(sesionClase.getId());
        dto.setDiaSemana(sesionClase.getDiaSemana());
        dto.setHoraInicio(sesionClase.getHoraInicio());
        dto.setHoraFin(sesionClase.getHoraFin());
        dto.setProgramacionAcademicaIds(sesionClase.getProgramacionesAcademicas().stream().map(ProgramacionAcademica::getId).collect(Collectors.toSet()));
        return dto;
    }
}
