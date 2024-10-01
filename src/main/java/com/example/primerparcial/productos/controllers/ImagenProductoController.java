package com.example.primerparcial.productos.controllers;

import com.example.primerparcial.productos.models.ImagenProducto;
import com.example.primerparcial.productos.services.ImagenProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/imagenes-producto")
public class ImagenProductoController {

    @Autowired
    private ImagenProductoService imagenProductoService;

    @GetMapping
    public ResponseEntity<List<ImagenProducto>> getAll() {
        List<ImagenProducto> imagenes = imagenProductoService.findAll();
        return ResponseEntity.ok(imagenes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagenProducto> getById(@PathVariable Long id) {
        Optional<ImagenProducto> imagen = imagenProductoService.findById(id);
        return imagen.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ImagenProducto> create(@RequestBody ImagenProducto imagenProducto) {
        ImagenProducto nuevaImagen = imagenProductoService.save(imagenProducto);
        return ResponseEntity.ok(nuevaImagen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImagenProducto> update(@PathVariable Long id, @RequestBody ImagenProducto detalles) {
        Optional<ImagenProducto> imagenExistente = imagenProductoService.findById(id);
        if (imagenExistente.isPresent()) {
            ImagenProducto imagen = imagenExistente.get();
            imagen.setRutaImagen(detalles.getRutaImagen());
            ImagenProducto actualizada = imagenProductoService.save(imagen);
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        imagenProductoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

