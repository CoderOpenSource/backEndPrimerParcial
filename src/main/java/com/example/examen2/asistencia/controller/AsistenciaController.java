package com.example.examen2.asistencia.controller;

import com.example.examen2.asistencia.model.AsistenciaDTO;
import com.example.examen2.asistencia.service.AsistenciaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/asistencias/")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir JSON a LicenciaDTO

    @GetMapping("/")
    public List<AsistenciaDTO> getAllAsistencias() {
        return asistenciaService.findAll();
    }

    @GetMapping("/{id}")
    public AsistenciaDTO getAsistenciaById(@PathVariable Long id) {
        return asistenciaService.findById(id);
    }

    @PostMapping("/createOrUpdate")
    public ResponseEntity<AsistenciaDTO> createAsistencia(
            @RequestPart("asistencia") String asistenciaJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            AsistenciaDTO asistenciaDTO = objectMapper.readValue(asistenciaJson, AsistenciaDTO.class);
            AsistenciaDTO savedAsistencia = asistenciaService.createAsistencia(asistenciaDTO, file);
            return ResponseEntity.ok(savedAsistencia);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsistenciaDTO> updateAsistencia(
            @PathVariable Long id,
            @RequestPart("asistencia") AsistenciaDTO asistenciaDTO,
            @RequestPart("file") MultipartFile file) {
        try {
            AsistenciaDTO updatedAsistencia = asistenciaService.updateAsistencia(id, asistenciaDTO, file);
            return ResponseEntity.ok(updatedAsistencia);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }


    @DeleteMapping("/{id}")
    public void deleteAsistencia(@PathVariable Long id) {
        asistenciaService.deleteAsistencia(id);
    }
}
