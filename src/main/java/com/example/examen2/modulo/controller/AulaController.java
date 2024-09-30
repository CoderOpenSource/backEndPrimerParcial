package com.example.examen2.modulo.controller;

import com.example.examen2.modulo.model.AulaDTO;
import com.example.examen2.modulo.service.AulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aulas/")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    @GetMapping
    public ResponseEntity<List<AulaDTO>> getAllAulas() {
        return ResponseEntity.ok(aulaService.findAll());
    }

    @PostMapping
    public ResponseEntity<AulaDTO> createAula(@RequestBody AulaDTO aulaDTO) {
        return ResponseEntity.ok(aulaService.createAula(aulaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AulaDTO> updateAula(@PathVariable Long id, @RequestBody AulaDTO aulaDTO) {
        return ResponseEntity.ok(aulaService.updateAula(id, aulaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAula(@PathVariable Long id) {
        aulaService.deleteAula(id);
        return ResponseEntity.noContent().build();
    }
}
