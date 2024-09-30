package com.example.examen2.asistencia.controller;

import com.example.examen2.asistencia.model.EstadoAsistenciaDTO;
import com.example.examen2.asistencia.service.EstadoAsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estadosasistencia/")
public class EstadoAsistenciaController {

    @Autowired
    private EstadoAsistenciaService estadoAsistenciaService;

    @GetMapping("/")
    public List<EstadoAsistenciaDTO> getAllEstadosAsistencia() {
        return estadoAsistenciaService.findAll();
    }

    @GetMapping("/{id}")
    public EstadoAsistenciaDTO getEstadoAsistenciaById(@PathVariable Long id) {
        return estadoAsistenciaService.findById(id);
    }

    @PostMapping("/")
    public EstadoAsistenciaDTO createEstadoAsistencia(@RequestBody EstadoAsistenciaDTO estadoAsistenciaDTO) {
        return estadoAsistenciaService.createEstadoAsistencia(estadoAsistenciaDTO);
    }

    @PutMapping("/{id}")
    public EstadoAsistenciaDTO updateEstadoAsistencia(@PathVariable Long id, @RequestBody EstadoAsistenciaDTO estadoAsistenciaDTO) {
        return estadoAsistenciaService.updateEstadoAsistencia(id, estadoAsistenciaDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEstadoAsistencia(@PathVariable Long id) {
        estadoAsistenciaService.deleteEstadoAsistencia(id);
    }
}
