package com.example.primerparcial.sucursales.controllers;


import com.example.primerparcial.productos.models.ProductoDetalle;
import com.example.primerparcial.productos.services.ProductoDetalleService;
import com.example.primerparcial.sucursales.models.Inventario;
import com.example.primerparcial.sucursales.models.Sucursal;
import com.example.primerparcial.sucursales.services.InventarioService;
import com.example.primerparcial.sucursales.services.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;
    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private ProductoDetalleService productoDetalleService;

    @GetMapping
    public ResponseEntity<List<Inventario>> getAllInventarios() {
        List<Inventario> inventarios = inventarioService.findAllInventarios();
        return ResponseEntity.ok(inventarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> getInventarioById(@PathVariable Long id) {
        Optional<Inventario> inventario = inventarioService.findById(id);
        return inventario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sucursal/{sucursalId}")
    public ResponseEntity<List<Inventario>> getInventarioBySucursal(@PathVariable Long sucursalId) {
        Sucursal sucursal = new Sucursal(); // Crear un objeto Sucursal con el ID
        sucursal.setId(sucursalId);
        List<Inventario> inventarios = inventarioService.findBySucursal(sucursal);
        return ResponseEntity.ok(inventarios);
    }

    // Guardar un nuevo inventario
    @PostMapping
    public ResponseEntity<Inventario> crearInventario(
            @RequestParam Long sucursalId,
            @RequestParam Long productoDetalleId,
            @RequestParam Integer cantidad) {

        Optional<Sucursal> sucursalOpt = sucursalService.findById(sucursalId);
        Optional<ProductoDetalle> productoDetalleOpt = productoDetalleService.findById(productoDetalleId);

        if (sucursalOpt.isPresent() && productoDetalleOpt.isPresent()) {
            Inventario nuevoInventario = new Inventario();
            nuevoInventario.setSucursal(sucursalOpt.get());
            nuevoInventario.setProductodetalle(productoDetalleOpt.get());
            nuevoInventario.setCantidad(cantidad);

            Inventario inventarioGuardado = inventarioService.saveInventario(nuevoInventario);
            return ResponseEntity.ok(inventarioGuardado);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Actualizar un inventario existente
    @PutMapping("/{id}")
    public ResponseEntity<Inventario> actualizarInventario(
            @PathVariable Long id,
            @RequestParam Long sucursalId,
            @RequestParam Long productoDetalleId,
            @RequestParam Integer cantidad) {

        Optional<Inventario> inventarioOpt = inventarioService.findById(id);
        Optional<Sucursal> sucursalOpt = sucursalService.findById(sucursalId);
        Optional<ProductoDetalle> productoDetalleOpt = productoDetalleService.findById(productoDetalleId);

        if (inventarioOpt.isPresent() && sucursalOpt.isPresent() && productoDetalleOpt.isPresent()) {
            Inventario inventario = inventarioOpt.get();
            inventario.setSucursal(sucursalOpt.get());
            inventario.setProductodetalle(productoDetalleOpt.get());
            inventario.setCantidad(cantidad);

            Inventario inventarioActualizado = inventarioService.saveInventario(inventario);
            return ResponseEntity.ok(inventarioActualizado);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventario(@PathVariable Long id) {
        inventarioService.deleteInventario(id);
        return ResponseEntity.noContent().build();
    }
}

