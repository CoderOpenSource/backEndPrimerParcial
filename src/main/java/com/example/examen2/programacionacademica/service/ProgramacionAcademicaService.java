package com.example.examen2.programacionacademica.service;

import com.example.examen2.docente.model.Docente;
import com.example.examen2.docente.repository.DocenteRepository;
import com.example.examen2.materia.repository.MateriaRepository;
import com.example.examen2.modulo.repository.AulaRepository;
import com.example.examen2.materia.repository.SemestreRepository;
import com.example.examen2.programacionacademica.model.ProgramacionAcademica;
import com.example.examen2.programacionacademica.model.ProgramacionAcademicaDTO;
import com.example.examen2.programacionacademica.model.SesionClase;
import com.example.examen2.programacionacademica.repository.ProgramacionAcademicaRepository;
import com.example.examen2.programacionacademica.repository.SesionClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProgramacionAcademicaService {

    @Autowired
    private ProgramacionAcademicaRepository programacionAcademicaRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private SemestreRepository semestreRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private SesionClaseRepository sesionClaseRepository;

    public List<ProgramacionAcademica> findAll() {
        return programacionAcademicaRepository.findAll();
    }

    @Transactional
    public ProgramacionAcademica createProgramacionAcademica(ProgramacionAcademicaDTO programacionAcademicaDTO) {
        System.out.println("Datos recibidos en createProgramacionAcademica: " + programacionAcademicaDTO.toString());
        ProgramacionAcademica programacionAcademica = mapDtoToEntity(programacionAcademicaDTO);
        return programacionAcademicaRepository.save(programacionAcademica);
    }

    @Transactional
    public ProgramacionAcademica updateProgramacionAcademica(Long id, ProgramacionAcademicaDTO programacionAcademicaDTO) {
        System.out.println("Datos recibidos en updateProgramacionAcademica: " + programacionAcademicaDTO.toString());
        ProgramacionAcademica existingProgramacionAcademica = programacionAcademicaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Programacion Academica not found with id: " + id));
        updateEntityFromDto(existingProgramacionAcademica, programacionAcademicaDTO);
        return programacionAcademicaRepository.save(existingProgramacionAcademica);
    }

    @Transactional
    public void deleteProgramacionAcademica(Long id) {
        ProgramacionAcademica existingProgramacionAcadematica = programacionAcademicaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Programacion Academica not found with id: " + id));

        // Eliminar relaciones con docentes antes de eliminar la programación académica
        existingProgramacionAcadematica.getDocentes().forEach(docente -> docente.getProgramacionesAcademicas().remove(existingProgramacionAcadematica));
        existingProgramacionAcadematica.setDocentes(new HashSet<>());
        programacionAcademicaRepository.save(existingProgramacionAcadematica);

        // Ahora podemos eliminar la programación académica
        programacionAcademicaRepository.delete(existingProgramacionAcadematica);
    }

    private ProgramacionAcademica mapDtoToEntity(ProgramacionAcademicaDTO dto) {
        ProgramacionAcademica programacionAcadematica = new ProgramacionAcademica();
        programacionAcadematica.setMateria(materiaRepository.findById(dto.getMateriaId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid materiaId: " + dto.getMateriaId())));
        programacionAcadematica.setAula(aulaRepository.findById(dto.getAulaId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid aulaId: " + dto.getAulaId())));
        programacionAcadematica.setSemestre(semestreRepository.findById(dto.getSemestreId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid semestreId: " + dto.getSemestreId())));

        if (dto.getDocenteIds() != null && !dto.getDocenteIds().isEmpty()) {
            Set<Docente> docentes = new HashSet<>(docenteRepository.findAllById(dto.getDocenteIds()));
            programacionAcadematica.setDocentes(docentes);
        } else {
            programacionAcadematica.setDocentes(new HashSet<>());
        }

        if (dto.getSesionClaseIds() != null && !dto.getSesionClaseIds().isEmpty()) {
            Set<SesionClase> sesiones = new HashSet<>(sesionClaseRepository.findAllById(dto.getSesionClaseIds()));
            programacionAcadematica.setSesiones(sesiones);
        } else {
            programacionAcadematica.setSesiones(new HashSet<>());
        }

        programacionAcadematica.setGrupo(dto.getGrupo());

        return programacionAcadematica;
    }

    private void updateEntityFromDto(ProgramacionAcademica programacionAcadematica, ProgramacionAcademicaDTO dto) {
        programacionAcadematica.setMateria(materiaRepository.findById(dto.getMateriaId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid materiaId: " + dto.getMateriaId())));
        programacionAcadematica.setAula(aulaRepository.findById(dto.getAulaId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid aulaId: " + dto.getAulaId())));
        System.out.println("prueba en el dto + " + dto.getSemestreId());
        programacionAcadematica.setSemestre(semestreRepository.findById(dto.getSemestreId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid semestreId: " + dto.getSemestreId())));

        if (dto.getDocenteIds() != null && !dto.getDocenteIds().isEmpty()) {
            Set<Docente> docentes = new HashSet<>(docenteRepository.findAllById(dto.getDocenteIds()));
            programacionAcadematica.setDocentes(docentes);
        } else {
            programacionAcadematica.setDocentes(new HashSet<>());
        }

        if (dto.getSesionClaseIds() != null && !dto.getSesionClaseIds().isEmpty()) {
            Set<SesionClase> sesiones = new HashSet<>(sesionClaseRepository.findAllById(dto.getSesionClaseIds()));
            programacionAcadematica.setSesiones(sesiones);
        } else {
            programacionAcadematica.setSesiones(new HashSet<>());
        }

        programacionAcadematica.setGrupo(dto.getGrupo());
    }
}
