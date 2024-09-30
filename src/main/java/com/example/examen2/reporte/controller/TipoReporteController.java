package com.example.examen2.reporte.controller;

import com.example.examen2.reporte.model.TipoReporteDTO;
import com.example.examen2.reporte.service.TipoReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tiporeportes/")
public class TipoReporteController {

    @Autowired
    private TipoReporteService tipoReporteService;

    @GetMapping
    public List<TipoReporteDTO> getAllTipoReportes() {
        return tipoReporteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoReporteDTO> getTipoReporteById(@PathVariable Long id) {
        TipoReporteDTO tipoReporteDTO = tipoReporteService.findById(id);
        return ResponseEntity.ok(tipoReporteDTO);
    }

    @PostMapping
    public ResponseEntity<TipoReporteDTO> createTipoReporte(@RequestBody TipoReporteDTO tipoReporteDTO) {
        TipoReporteDTO createdTipoReporte = tipoReporteService.createTipoReporte(tipoReporteDTO);
        return ResponseEntity.ok(createdTipoReporte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoReporteDTO> updateTipoReporte(@PathVariable Long id, @RequestBody TipoReporteDTO tipoReporteDTO) {
        TipoReporteDTO updatedTipoReporte = tipoReporteService.updateTipoReporte(id, tipoReporteDTO);
        return ResponseEntity.ok(updatedTipoReporte);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoReporte(@PathVariable Long id) {
        tipoReporteService.deleteTipoReporte(id);
        return ResponseEntity.noContent().build();
    }
}
