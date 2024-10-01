package com.example.primerparcial.sucursales.controllers;

import com.example.primerparcial.sucursales.models.Sucursal;
import com.example.primerparcial.sucursales.services.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<Sucursal>> getAllSucursales() {
        List<Sucursal> sucursales = sucursalService.findAllSucursales();
        return ResponseEntity.ok(sucursales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> getSucursalById(@PathVariable Long id) {
        Optional<Sucursal> sucursal = sucursalService.findById(id);
        return sucursal.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Sucursal> createSucursal(@RequestBody Sucursal sucursal) {
        Sucursal nuevaSucursal = sucursalService.saveSucursal(sucursal);
        return ResponseEntity.ok(nuevaSucursal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> updateSucursal(@PathVariable Long id, @RequestBody Sucursal sucursalDetalles) {
        Optional<Sucursal> sucursalExistente = sucursalService.findById(id);

        if (sucursalExistente.isPresent()) {
            Sucursal sucursal = sucursalExistente.get();
            sucursal.setNombre(sucursalDetalles.getNombre());
            sucursal.setDireccion(sucursalDetalles.getDireccion());
            sucursal.setTelefono(sucursalDetalles.getTelefono());
            sucursal.setLatitud(sucursalDetalles.getLatitud());
            sucursal.setLongitud(sucursalDetalles.getLongitud());

            Sucursal sucursalActualizada = sucursalService.saveSucursal(sucursal);
            return ResponseEntity.ok(sucursalActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSucursal(@PathVariable Long id) {
        sucursalService.deleteSucursal(id);
        return ResponseEntity.noContent().build();
    }
}
