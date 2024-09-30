package com.example.examen2.asistencia.controller;

import com.example.examen2.asistencia.model.LicenciaDTO;
import com.example.examen2.asistencia.service.LicenciaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/licencias/")
public class LicenciaController {

    @Autowired
    private LicenciaService licenciaService;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir JSON a LicenciaDTO

    @GetMapping
    public List<LicenciaDTO> getAllLicencias() {
        return licenciaService.findAll();
    }

    @GetMapping("/{id}")
    public LicenciaDTO getLicenciaById(@PathVariable Long id) {
        return licenciaService.findById(id);
    }

    @PostMapping("/createOrUpdate")
    public ResponseEntity<LicenciaDTO> createOrUpdateLicencia(
            @RequestPart("licencia") String licenciaJson,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            LicenciaDTO licenciaDTO = objectMapper.readValue(licenciaJson, LicenciaDTO.class);
            LicenciaDTO savedLicencia = licenciaService.createOrUpdateLicencia(licenciaDTO, file);
            return ResponseEntity.ok(savedLicencia);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteLicencia(@PathVariable Long id) {
        licenciaService.deleteLicencia(id);
    }
}
