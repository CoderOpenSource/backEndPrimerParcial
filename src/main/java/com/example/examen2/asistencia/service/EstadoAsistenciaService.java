package com.example.examen2.asistencia.service;

import com.example.examen2.asistencia.model.EstadoAsistencia;
import com.example.examen2.asistencia.model.EstadoAsistenciaDTO;
import com.example.examen2.asistencia.repository.EstadoAsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadoAsistenciaService {

    @Autowired
    private EstadoAsistenciaRepository estadoAsistenciaRepository;

    public List<EstadoAsistenciaDTO> findAll() {
        return estadoAsistenciaRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public EstadoAsistenciaDTO findById(Long id) {
        EstadoAsistencia estadoAsistencia = estadoAsistenciaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("EstadoAsistencia not found with id: " + id));
        return mapEntityToDto(estadoAsistencia);
    }

    @Transactional
    public EstadoAsistenciaDTO createEstadoAsistencia(EstadoAsistenciaDTO estadoAsistenciaDTO) {
        EstadoAsistencia estadoAsistencia = mapDtoToEntity(estadoAsistenciaDTO);
        return mapEntityToDto(estadoAsistenciaRepository.save(estadoAsistencia));
    }

    @Transactional
    public EstadoAsistenciaDTO updateEstadoAsistencia(Long id, EstadoAsistenciaDTO estadoAsistenciaDTO) {
        EstadoAsistencia existingEstadoAsistencia = estadoAsistenciaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("EstadoAsistencia not found with id: " + id));
        updateEntityFromDto(existingEstadoAsistencia, estadoAsistenciaDTO);
        return mapEntityToDto(estadoAsistenciaRepository.save(existingEstadoAsistencia));
    }

    @Transactional
    public void deleteEstadoAsistencia(Long id) {
        EstadoAsistencia estadoAsistencia = estadoAsistenciaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("EstadoAsistencia not found with id: " + id));
        estadoAsistenciaRepository.delete(estadoAsistencia);
    }

    private EstadoAsistencia mapDtoToEntity(EstadoAsistenciaDTO dto) {
        EstadoAsistencia estadoAsistencia = new EstadoAsistencia();
        estadoAsistencia.setNombre(dto.getNombre());
        return estadoAsistencia;
    }

    private void updateEntityFromDto(EstadoAsistencia estadoAsistencia, EstadoAsistenciaDTO dto) {
        estadoAsistencia.setNombre(dto.getNombre());
    }

    private EstadoAsistenciaDTO mapEntityToDto(EstadoAsistencia estadoAsistencia) {
        EstadoAsistenciaDTO dto = new EstadoAsistenciaDTO();
        dto.setId(estadoAsistencia.getId());
        dto.setNombre(estadoAsistencia.getNombre());
        return dto;
    }
}
