package com.example.primerparcial.productos.controllers;

import com.example.primerparcial.productos.models.Modelo3D;
import com.example.primerparcial.productos.services.Modelo3DService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/modelos3d")
public class Modelo3DController {

    @Autowired
    private Modelo3DService modelo3DService;

    @GetMapping
    public ResponseEntity<List<Modelo3D>> getAll() {
        List<Modelo3D> modelos = modelo3DService.findAll();
        return ResponseEntity.ok(modelos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modelo3D> getById(@PathVariable Long id) {
        Optional<Modelo3D> modelo = modelo3DService.findById(id);
        return modelo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Modelo3D> create(@RequestBody Modelo3D modelo3D) {
        Modelo3D nuevoModelo = modelo3DService.save(modelo3D);
        return ResponseEntity.ok(nuevoModelo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Modelo3D> update(@PathVariable Long id, @RequestBody Modelo3D detalles) {
        Optional<Modelo3D> modeloExistente = modelo3DService.findById(id);
        if (modeloExistente.isPresent()) {
            Modelo3D modelo = modeloExistente.get();
            modelo.setRutaModelo3D(detalles.getRutaModelo3D());
            Modelo3D actualizada = modelo3DService.save(modelo);
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        modelo3DService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

