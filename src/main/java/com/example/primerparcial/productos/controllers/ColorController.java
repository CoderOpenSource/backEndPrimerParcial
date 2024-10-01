package com.example.primerparcial.productos.controllers;


import com.example.primerparcial.productos.models.Color;
import com.example.primerparcial.productos.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/colores")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @GetMapping
    public ResponseEntity<List<Color>> getAll() {
        List<Color> colores = colorService.findAll();
        return ResponseEntity.ok(colores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Color> getById(@PathVariable Long id) {
        Optional<Color> color = colorService.findById(id);
        return color.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Color> create(@RequestBody Color color) {
        Color nuevoColor = colorService.save(color);
        return ResponseEntity.ok(nuevoColor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Color> update(@PathVariable Long id, @RequestBody Color colorDetalles) {
        Optional<Color> colorExistente = colorService.findById(id);
        if (colorExistente.isPresent()) {
            Color color = colorExistente.get();
            color.setNombre(colorDetalles.getNombre());
            Color actualizado = colorService.save(color);
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        colorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
