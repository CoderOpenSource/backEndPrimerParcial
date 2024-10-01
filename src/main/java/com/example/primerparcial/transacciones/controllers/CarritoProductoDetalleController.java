package com.example.primerparcial.transacciones.controllers;

import com.example.primerparcial.productos.models.ProductoDetalle;
import com.example.primerparcial.transacciones.models.Carrito;
import com.example.primerparcial.transacciones.models.CarritoProductoDetalle;
import com.example.primerparcial.transacciones.models.CarritoProductoDetalleDTO;
import com.example.primerparcial.transacciones.service.CarritoProductoDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/carrito-producto-detalles")
public class CarritoProductoDetalleController {

    @Autowired
    private CarritoProductoDetalleService carritoProductoDetalleService;

    @GetMapping
    public ResponseEntity<List<CarritoProductoDetalleDTO>> getAll() {
        List<CarritoProductoDetalle> detalles = carritoProductoDetalleService.findAll();
        List<CarritoProductoDetalleDTO> detallesDTO = detalles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(detallesDTO);
    }

    @PostMapping
    public ResponseEntity<CarritoProductoDetalle> create(@RequestBody CarritoProductoDetalleDTO carritoProductoDetalleDTO) {
        // Buscar el carrito por el ID
        Carrito carrito = carritoProductoDetalleService.findCarritoById(carritoProductoDetalleDTO.getCarritoId());
        if (carrito == null) {
            System.out.println("Error: No se encontró el carrito con ID: " + carritoProductoDetalleDTO.getCarritoId());
            return ResponseEntity.badRequest().body(null);
        }

        // Buscar el producto detalle por el ID
        ProductoDetalle productoDetalle = carritoProductoDetalleService.findProductoDetalleById(carritoProductoDetalleDTO.getProductoDetalleId());
        if (productoDetalle == null) {
            System.out.println("Error: No se encontró el producto detalle con ID: " + carritoProductoDetalleDTO.getProductoDetalleId());
            return ResponseEntity.badRequest().body(null);
        }

        // Crear el objeto CarritoProductoDetalle
        CarritoProductoDetalle carritoProductoDetalle = new CarritoProductoDetalle();
        carritoProductoDetalle.setCarrito(carrito);
        carritoProductoDetalle.setProductoDetalle(productoDetalle);
        carritoProductoDetalle.setCantidad(carritoProductoDetalleDTO.getCantidad());

        // Guardar el detalle
        CarritoProductoDetalle nuevoDetalle = carritoProductoDetalleService.save(carritoProductoDetalle);
        return ResponseEntity.ok(nuevoDetalle);
    }



    @GetMapping("/{id}")
    public ResponseEntity<CarritoProductoDetalleDTO> getById(@PathVariable Long id) {
        CarritoProductoDetalle detalle = carritoProductoDetalleService.findById(id);
        return detalle != null ? ResponseEntity.ok(convertToDTO(detalle)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carritoProductoDetalleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Método de conversión de CarritoProductoDetalle a CarritoProductoDetalleDTO
    private CarritoProductoDetalleDTO convertToDTO(CarritoProductoDetalle detalle) {
        CarritoProductoDetalleDTO dto = new CarritoProductoDetalleDTO();
        dto.setId(detalle.getId());

        // Validar si el carrito es nulo
        if (detalle.getCarrito() != null) {
            dto.setCarritoId(detalle.getCarrito().getId());
        } else {
            System.out.println("Error: El carrito es nulo para el detalle con ID: " + detalle.getId());
            dto.setCarritoId(null);  // Puedes manejar este caso según la lógica que prefieras
        }

        // Validar si el producto detalle es nulo
        if (detalle.getProductoDetalle() != null) {
            dto.setProductoDetalleId(detalle.getProductoDetalle().getId());
        } else {
            System.out.println("Error: El producto detalle es nulo para el detalle con ID: " + detalle.getId());
            dto.setProductoDetalleId(null);  // Puedes manejar este caso según la lógica que prefieras
        }

        dto.setCantidad(detalle.getCantidad());
        return dto;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarritoProductoDetalleDTO> update(@PathVariable Long id, @RequestBody CarritoProductoDetalleDTO carritoProductoDetalleDTO) {
        CarritoProductoDetalle detalleExistente = carritoProductoDetalleService.findById(id);
        if (detalleExistente == null) {
            return ResponseEntity.notFound().build();
        }

        // Actualizar los valores del detalle existente
        detalleExistente.setCarrito(carritoProductoDetalleService.findCarritoById(carritoProductoDetalleDTO.getCarritoId())); // Método en el servicio para encontrar carrito por ID
        detalleExistente.setProductoDetalle(carritoProductoDetalleService.findProductoDetalleById(carritoProductoDetalleDTO.getProductoDetalleId())); // Método en el servicio para encontrar producto detalle por ID
        detalleExistente.setCantidad(carritoProductoDetalleDTO.getCantidad());

        // Guardar los cambios
        CarritoProductoDetalle detalleActualizado = carritoProductoDetalleService.save(detalleExistente);

        return ResponseEntity.ok(convertToDTO(detalleActualizado));
    }

}
