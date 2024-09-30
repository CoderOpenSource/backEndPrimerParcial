package com.example.examen2.carrera.controller;

import com.example.examen2.carrera.model.FacultadDTO;
import com.example.examen2.carrera.model.Facultad;
import com.example.examen2.carrera.service.FacultadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facultades/")
public class FacultadController {

    @Autowired
    private FacultadService facultadService;

    @PostMapping
    public ResponseEntity<Facultad> createFacultad(@RequestBody FacultadDTO facultadDTO) {
        Facultad createdFacultad = facultadService.createFacultad(facultadDTO);
        return ResponseEntity.ok(createdFacultad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Facultad> updateFacultad(@PathVariable Long id, @RequestBody FacultadDTO facultadDTO) {
        Facultad updatedFacultad = facultadService.updateFacultad(id, facultadDTO);
        return ResponseEntity.ok(updatedFacultad);
    }

    @GetMapping
    public ResponseEntity<List<Facultad>> getAllFacultades() {
        List<Facultad> facultades = facultadService.findAll();
        return ResponseEntity.ok(facultades);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacultad(@PathVariable Long id) {
        facultadService.deleteFacultad(id);
        return ResponseEntity.noContent().build();
    }
}
