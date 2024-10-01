package com.example.primerparcial.productos.controllers;

import com.example.primerparcial.productos.models.Subcategoria;
import com.example.primerparcial.productos.services.SubcategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController

@RequestMapping("/api/subcategorias")
public class SubcategoriaController {

    @Autowired
    private SubcategoriaService subcategoriaService;

    @GetMapping
    public ResponseEntity<List<Subcategoria>> getAll() {
        List<Subcategoria> subcategorias = subcategoriaService.findAll();
        return ResponseEntity.ok(subcategorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subcategoria> getById(@PathVariable Long id) {
        Optional<Subcategoria> subcategoria = subcategoriaService.findById(id);
        return subcategoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Subcategoria> create(@RequestBody Subcategoria subcategoria) {
        Subcategoria nuevaSubcategoria = subcategoriaService.save(subcategoria);
        return ResponseEntity.ok(nuevaSubcategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subcategoria> update(@PathVariable Long id, @RequestBody Subcategoria subcategoriaDetalles) {
        Optional<Subcategoria> subcategoriaExistente = subcategoriaService.findById(id);
        if (subcategoriaExistente.isPresent()) {
            Subcategoria subcategoria = subcategoriaExistente.get();
            subcategoria.setNombre(subcategoriaDetalles.getNombre());
            subcategoria.setCategoria(subcategoriaDetalles.getCategoria());
            Subcategoria actualizada = subcategoriaService.save(subcategoria);
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subcategoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
