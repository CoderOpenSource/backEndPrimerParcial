package com.example.primerparcial.transacciones.controllers;

import com.example.primerparcial.transacciones.models.Carrito;
import com.example.primerparcial.transacciones.models.CarritoDTO;
import com.example.primerparcial.transacciones.models.CarritoProductoDetalleDTO;
import com.example.primerparcial.transacciones.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    public ResponseEntity<List<CarritoDTO>> getAll() {
        List<Carrito> carritos = carritoService.findAll();
        List<CarritoDTO> carritoDTOs = carritos.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(carritoDTOs);
    }

    // Método de conversión de Carrito a CarritoDTO
    private CarritoDTO convertToDTO(Carrito carrito) {
        CarritoDTO dto = new CarritoDTO();
        dto.setId(carrito.getId());
        dto.setUsuarioId(carrito.getUsuario().getId());
        dto.setDisponible(carrito.isDisponible());

        List<CarritoProductoDetalleDTO> detallesDTO = carrito.getProductosDetalle().stream()
                .map(detalle -> {
                    CarritoProductoDetalleDTO detalleDTO = new CarritoProductoDetalleDTO();
                    detalleDTO.setId(detalle.getId());
                    detalleDTO.setProductoDetalleId(detalle.getProductoDetalle().getId());
                    detalleDTO.setCantidad(detalle.getCantidad());
                    return detalleDTO;
                }).collect(Collectors.toList());

        dto.setProductosDetalle(detallesDTO);
        return dto;
    }

    @PostMapping
    public ResponseEntity<Carrito> create(@RequestBody Carrito carrito) {
        Carrito nuevoCarrito = carritoService.save(carrito);
        return ResponseEntity.ok(nuevoCarrito);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarritoDTO> getById(@PathVariable Long id) {
        Carrito carrito = carritoService.findById(id);
        return carrito != null ? ResponseEntity.ok(convertToDTO(carrito)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carritoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrito> update(@PathVariable Long id, @RequestBody Carrito carritoRequest) {
        Carrito carritoExistente = carritoService.findById(id);
        if (carritoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        carritoExistente.setUsuario(carritoRequest.getUsuario());
        carritoExistente.setDisponible(carritoRequest.isDisponible());
        Carrito carritoActualizado = carritoService.save(carritoExistente);
        return ResponseEntity.ok(carritoActualizado);
    }
}
