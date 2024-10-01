package com.example.primerparcial.transacciones.controllers;

import com.example.primerparcial.productos.models.ProductoDetalle;
import com.example.primerparcial.sucursales.models.Inventario;
import com.example.primerparcial.sucursales.services.InventarioService;
import com.example.primerparcial.transacciones.models.*;
import com.example.primerparcial.transacciones.service.CarritoService;
import com.example.primerparcial.transacciones.service.FacturaService;
import com.example.primerparcial.transacciones.service.TipoPagoService;
import com.example.primerparcial.transacciones.service.TransaccionService;
import com.example.primerparcial.usuarios.models.Usuario;
import com.example.primerparcial.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private TipoPagoService tipoPagoService;

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public ResponseEntity<List<TransaccionDTO>> getAll() {
        List<Transaccion> transacciones = transaccionService.findAll();

        // Convertimos las transacciones en TransaccionDTO
        List<TransaccionDTO> transaccionesDTO = transacciones.stream().map(this::convertirADTO).collect(Collectors.toList());

        return ResponseEntity.ok(transaccionesDTO);
    }

    @Autowired
    private InventarioService inventarioService;

    @PostMapping
    public ResponseEntity<Transaccion> create(@RequestBody Map<String, Object> transaccionData) {
        System.out.println("Request body: " + transaccionData);

        Long usuarioId = Long.parseLong((String) transaccionData.get("usuario_id"));
        Long carritoId = Long.parseLong((String) transaccionData.get("carrito_id"));
        Long tipoPagoId = Long.parseLong((String) transaccionData.get("tipo_pago_id"));

        Usuario usuario = usuarioService.findById(usuarioId);
        Carrito carrito = carritoService.findById(carritoId);
        TipoPago tipoPago = tipoPagoService.findById(tipoPagoId);

        if (usuario == null || carrito == null || tipoPago == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Crear la transacción
        Transaccion nuevaTransaccion = new Transaccion(usuario, carrito, tipoPago);
        Transaccion transaccionGuardada = transaccionService.save(nuevaTransaccion);

        // Actualizar el inventario para cada producto en el carrito
        for (CarritoProductoDetalle detalle : carrito.getProductosDetalle()) {
            ProductoDetalle productoDetalle = detalle.getProductoDetalle();
            Optional<Inventario> inventarioOpt = inventarioService.findByProductoDetalle(productoDetalle);

            if (inventarioOpt.isPresent()) {
                Inventario inventario = inventarioOpt.get();

                // Restar la cantidad
                if (inventario.getCantidad() >= detalle.getCantidad()) {
                    inventario.setCantidad(inventario.getCantidad() - detalle.getCantidad());
                    inventarioService.saveInventario(inventario);
                } else {
                    return ResponseEntity.badRequest().body(null); // No hay suficiente inventario
                }
            } else {
                return ResponseEntity.badRequest().body(null); // No se encontró el producto en inventario
            }
        }

        return ResponseEntity.ok(transaccionGuardada);
    }


    // Actualizar transacción recibiendo directamente un Map con los IDs
    @PutMapping("/{id}")
    public ResponseEntity<Transaccion> update(@PathVariable Long id, @RequestBody Map<String, Object> transaccionData) {
        Transaccion transaccionExistente = transaccionService.findById(id);
        if (transaccionExistente == null) {
            return ResponseEntity.notFound().build();
        }

        // Convertimos los IDs de String a Long
        Long usuarioId = Long.parseLong((String) transaccionData.get("usuario_id"));
        Long carritoId = Long.parseLong((String) transaccionData.get("carrito_id"));
        Long tipoPagoId = Long.parseLong((String) transaccionData.get("tipo_pago_id"));

        // Buscar los objetos relacionados
        Usuario usuario = usuarioService.findById(usuarioId);
        Carrito carrito = carritoService.findById(carritoId);
        TipoPago tipoPago = tipoPagoService.findById(tipoPagoId);

        if (usuario == null || carrito == null || tipoPago == null) {
            return ResponseEntity.badRequest().body(null);  // Verificamos que existan
        }

        // Actualizamos la transacción
        transaccionExistente.setUsuario(usuario);
        transaccionExistente.setCarrito(carrito);
        transaccionExistente.setTipoPago(tipoPago);

        Transaccion transaccionActualizada = transaccionService.save(transaccionExistente);
        return ResponseEntity.ok(transaccionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // 1. Obtener la transacción por su ID
        Transaccion transaccion = transaccionService.findById(id);
        if (transaccion == null) {
            return ResponseEntity.notFound().build();
        }

        // 2. Eliminar todas las facturas asociadas a la transacción
        List<Factura> facturas = facturaService.findByTransaccionId(transaccion.getId());
        if (!facturas.isEmpty()) {
            for (Factura factura : facturas) {
                facturaService.delete(factura.getId());
            }
        }

        // 3. Eliminar la transacción
        transaccionService.delete(transaccion.getId());

        return ResponseEntity.noContent().build();
    }

    // Método privado para convertir Transaccion a TransaccionDTO
    private TransaccionDTO convertirADTO(Transaccion transaccion) {
        return new TransaccionDTO(
                transaccion.getId(),
                transaccion.getUsuario().getNombre(),
                transaccion.getCarrito().getId(),
                transaccion.getTipoPago().getNombre(),
                transaccion.getTipoPago().getImagenQr(),
                transaccion.getFecha()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionDTO> getById(@PathVariable Long id) {
        Transaccion transaccion = transaccionService.findById(id);
        if (transaccion == null) {
            return ResponseEntity.notFound().build();
        }
        TransaccionDTO transaccionDTO = convertirADTO(transaccion);
        return ResponseEntity.ok(transaccionDTO);
    }
}
