package com.example.examen2.materia.controller;

import com.example.examen2.materia.model.MateriaDTO;
import com.example.examen2.materia.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materias/")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @GetMapping
    public ResponseEntity<List<MateriaDTO>> getAllMaterias() {
        List<MateriaDTO> materias = materiaService.getAllMaterias();
        return ResponseEntity.ok(materias);
    }

    @PostMapping
    public ResponseEntity<MateriaDTO> createMateria(@RequestBody MateriaDTO materiaDTO) {
        MateriaDTO createdMateria = materiaService.createMateria(materiaDTO);
        return ResponseEntity.ok(createdMateria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaDTO> updateMateria(@PathVariable Long id, @RequestBody MateriaDTO materiaDTO) {
        MateriaDTO updatedMateria = materiaService.updateMateria(id, materiaDTO);
        return ResponseEntity.ok(updatedMateria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMateria(@PathVariable Long id) {
        materiaService.deleteMateria(id);
        return ResponseEntity.noContent().build();
    }
}
