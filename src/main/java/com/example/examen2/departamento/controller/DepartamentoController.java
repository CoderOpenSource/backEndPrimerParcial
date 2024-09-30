package com.example.examen2.departamento.controller;

import com.example.examen2.departamento.model.DepartamentoDTO;
import com.example.examen2.departamento.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamentos/")
public class DepartamentoController {
    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public List<DepartamentoDTO> getAll() {
        return departamentoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(departamentoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DepartamentoDTO> create(@RequestBody DepartamentoDTO departamentoDTO) {
        return ResponseEntity.ok(departamentoService.createDepartamento(departamentoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> update(@PathVariable Long id, @RequestBody DepartamentoDTO departamentoDTO) {
        return ResponseEntity.ok(departamentoService.updateDepartamento(id, departamentoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departamentoService.deleteDepartamento(id);
        return ResponseEntity.noContent().build();
    }
}
