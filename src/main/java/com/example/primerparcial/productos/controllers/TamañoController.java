package com.example.primerparcial.productos.controllers;

import com.example.primerparcial.productos.models.Tamaño;
import com.example.primerparcial.productos.services.TamañoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tamaños")
public class TamañoController {

    @Autowired
    private TamañoService tamañoService;

    @GetMapping
    public ResponseEntity<List<Tamaño>> getAll() {
        List<Tamaño> tamaños = tamañoService.findAll();
        return ResponseEntity.ok(tamaños);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tamaño> getById(@PathVariable Long id) {
        Optional<Tamaño> tamaño = tamañoService.findById(id);
        return tamaño.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tamaño> create(@RequestBody Tamaño tamaño) {
        Tamaño nuevoTamaño = tamañoService.save(tamaño);
        return ResponseEntity.ok(nuevoTamaño);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tamaño> update(@PathVariable Long id, @RequestBody Tamaño tamañoDetalles) {
        Optional<Tamaño> tamañoExistente = tamañoService.findById(id);
        if (tamañoExistente.isPresent()) {
            Tamaño tamaño = tamañoExistente.get();
            tamaño.setNombre(tamañoDetalles.getNombre());
            tamaño.setDimensiones(tamañoDetalles.getDimensiones());
            Tamaño actualizado = tamañoService.save(tamaño);
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tamañoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

