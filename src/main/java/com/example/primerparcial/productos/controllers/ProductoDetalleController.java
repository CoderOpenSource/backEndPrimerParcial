package com.example.primerparcial.productos.controllers;

import com.example.primerparcial.productos.models.ProductoDetalle;
import com.example.primerparcial.productos.services.ProductoDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/productos-detalles")
public class ProductoDetalleController {

    @Autowired
    private ProductoDetalleService productoDetalleService;

    @GetMapping
    public ResponseEntity<List<ProductoDetalle>> getAll() {
        List<ProductoDetalle> detalles = productoDetalleService.findAll();
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDetalle> getById(@PathVariable Long id) {
        Optional<ProductoDetalle> detalle = productoDetalleService.findById(id);
        return detalle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductoDetalle> create(@RequestPart("productoDetalle") ProductoDetalle detalle,
                                                  @RequestPart(value = "imagen2D", required = false) MultipartFile file) {
        try {
            ProductoDetalle nuevoDetalle = productoDetalleService.save(detalle, file);
            return ResponseEntity.ok(nuevoDetalle);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Método para actualizar un ProductoDetalle existente
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDetalle> update(@PathVariable Long id,
                                                  @RequestPart("productoDetalle") ProductoDetalle detalles,
                                                  @RequestPart(value = "imagen2D", required = false) MultipartFile file) {
        try {
            ProductoDetalle actualizado = productoDetalleService.update(id, detalles, file);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoDetalleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
