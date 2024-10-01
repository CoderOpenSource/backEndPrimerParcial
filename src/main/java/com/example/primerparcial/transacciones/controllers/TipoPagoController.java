package com.example.primerparcial.transacciones.controllers;

import com.example.primerparcial.transacciones.models.TipoPago;
import com.example.primerparcial.transacciones.service.TipoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tipo-pagos")
public class TipoPagoController {

    @Autowired
    private TipoPagoService tipoPagoService;

    @GetMapping
    public ResponseEntity<List<TipoPago>> getAll() {
        List<TipoPago> tiposPago = tipoPagoService.findAll();
        return ResponseEntity.ok(tiposPago);
    }

    @PostMapping
    public ResponseEntity<TipoPago> create(@RequestBody TipoPago tipoPago) {
        TipoPago nuevoTipoPago = tipoPagoService.save(tipoPago);
        return ResponseEntity.ok(nuevoTipoPago);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoPago> getById(@PathVariable Long id) {
        TipoPago tipoPago = tipoPagoService.findById(id);
        return ResponseEntity.ok(tipoPago);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoPagoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<TipoPago> update(@PathVariable Long id, @RequestBody TipoPago tipoPago) {
        TipoPago tipoPagoExistente = tipoPagoService.findById(id);
        if (tipoPagoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        // Actualizamos los campos del tipoPago existente
        tipoPagoExistente.setNombre(tipoPago.getNombre());
        tipoPagoExistente.setImagenQr(tipoPago.getImagenQr());

        // Guardamos el tipoPago actualizado
        TipoPago tipoPagoActualizado = tipoPagoService.save(tipoPagoExistente);

        return ResponseEntity.ok(tipoPagoActualizado);
    }

}

