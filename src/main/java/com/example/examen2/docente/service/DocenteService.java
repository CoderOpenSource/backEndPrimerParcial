package com.example.examen2.docente.service;

import com.example.examen2.docente.model.Docente;
import com.example.examen2.docente.model.DocenteDTO;
import com.example.examen2.docente.repository.DocenteRepository;
import com.example.examen2.programacionacademica.repository.ProgramacionAcademicaRepository;
import com.example.examen2.departamento.repository.DepartamentoRepository;
import com.example.examen2.common.repository.RolRepository;
import com.example.examen2.programacionacademica.model.ProgramacionAcademica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private ProgramacionAcademicaRepository programacionAcademicaRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<DocenteDTO> findAll() {
        return docenteRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<DocenteDTO> findById(Long id) {
        return docenteRepository.findById(id).map(this::convertToDTO);
    }

    public DocenteDTO save(DocenteDTO docenteDTO) {
        Docente docente = convertToEntity(docenteDTO);
        return convertToDTO(docenteRepository.save(docente));
    }

    public void deleteById(Long id) {
        docenteRepository.deleteById(id);
    }

    private DocenteDTO convertToDTO(Docente docente) {
        DocenteDTO dto = new DocenteDTO();
        dto.setId(docente.getId());
        dto.setNombre(docente.getNombre());
        dto.setApellido(docente.getApellido());
        dto.setEmail(docente.getEmail());
        dto.setDepartamentoId(docente.getDepartamento().getId());
        dto.setProgramacionAcademicaIds(docente.getProgramacionesAcademicas().stream().map(ProgramacionAcademica::getId).collect(Collectors.toSet()));
        dto.setRolId(docente.getRol().getId()); // Agregado
        dto.setContrasena(docente.getContrasena()); // Agregado
        return dto;
    }

    private Docente convertToEntity(DocenteDTO dto) {
        Docente docente = new Docente();
        docente.setId(dto.getId());
        docente.setNombre(dto.getNombre());
        docente.setApellido(dto.getApellido());
        docente.setEmail(dto.getEmail());
        docente.setContrasena(passwordEncoder.encode(dto.getContrasena())); // Hasheado de contraseña
        docente.setRol(rolRepository.findById(dto.getRolId()).orElse(null)); // Asignar rol
        docente.setDepartamento(departamentoRepository.findById(dto.getDepartamentoId()).orElse(null));
        docente.setProgramacionesAcademicas(programacionAcademicaRepository.findAllById(dto.getProgramacionAcademicaIds()).stream().collect(Collectors.toSet()));
        return docente;
    }
}
