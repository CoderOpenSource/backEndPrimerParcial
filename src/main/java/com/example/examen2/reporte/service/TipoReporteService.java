package com.example.examen2.reporte.service;

import com.example.examen2.reporte.model.TipoReporte;
import com.example.examen2.reporte.model.TipoReporteDTO;
import com.example.examen2.reporte.repository.TipoReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoReporteService {

    @Autowired
    private TipoReporteRepository tipoReporteRepository;

    public List<TipoReporteDTO> findAll() {
        return tipoReporteRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public TipoReporteDTO findById(Long id) {
        TipoReporte tipoReporte = tipoReporteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TipoReporte not found with id: " + id));
        return mapEntityToDto(tipoReporte);
    }

    public TipoReporteDTO createTipoReporte(TipoReporteDTO tipoReporteDTO) {
        TipoReporte tipoReporte = mapDtoToEntity(tipoReporteDTO);
        return mapEntityToDto(tipoReporteRepository.save(tipoReporte));
    }

    public TipoReporteDTO updateTipoReporte(Long id, TipoReporteDTO tipoReporteDTO) {
        TipoReporte existingTipoReporte = tipoReporteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TipoReporte not found with id: " + id));
        existingTipoReporte.setNombre(tipoReporteDTO.getNombre());
        return mapEntityToDto(tipoReporteRepository.save(existingTipoReporte));
    }

    public void deleteTipoReporte(Long id) {
        TipoReporte tipoReporte = tipoReporteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TipoReporte not found with id: " + id));
        tipoReporteRepository.delete(tipoReporte);
    }

    private TipoReporte mapDtoToEntity(TipoReporteDTO dto) {
        TipoReporte tipoReporte = new TipoReporte();
        tipoReporte.setNombre(dto.getNombre());
        return tipoReporte;
    }

    private TipoReporteDTO mapEntityToDto(TipoReporte tipoReporte) {
        TipoReporteDTO dto = new TipoReporteDTO();
        dto.setId(tipoReporte.getId());
        dto.setNombre(tipoReporte.getNombre());
        return dto;
    }
}

