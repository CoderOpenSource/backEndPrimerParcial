package com.example.examen2.docente.controller;

import com.example.examen2.docente.model.DocenteDTO;
import com.example.examen2.docente.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docentes/")
public class DocenteController {
    @Autowired
    private DocenteService docenteService;

    @GetMapping
    public List<DocenteDTO> getAll() {
        return docenteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocenteDTO> getById(@PathVariable Long id) {
        return docenteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DocenteDTO> create(@RequestBody DocenteDTO docenteDTO) {
        return ResponseEntity.ok(docenteService.save(docenteDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocenteDTO> update(@PathVariable Long id, @RequestBody DocenteDTO docenteDTO) {
        return docenteService.findById(id)
                .map(existing -> {
                    docenteDTO.setId(id);  // Ensure the ID is set correctly
                    return ResponseEntity.ok(docenteService.save(docenteDTO));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return docenteService.findById(id)
                .map(existing -> {
                    docenteService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
