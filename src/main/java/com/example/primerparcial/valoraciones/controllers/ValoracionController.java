package com.example.primerparcial.valoraciones.controllers;

import com.example.primerparcial.productos.models.Producto;
import com.example.primerparcial.productos.services.ProductoService;
import com.example.primerparcial.usuarios.models.Usuario;
import com.example.primerparcial.usuarios.services.UsuarioService;
import com.example.primerparcial.valoraciones.models.Valoracion;
import com.example.primerparcial.valoraciones.services.ValoracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/valoraciones")
public class ValoracionController {

    @Autowired
    private ValoracionService valoracionService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Valoracion>> getAllValoraciones() {
        List<Valoracion> valoraciones = valoracionService.findAllValoraciones();
        return ResponseEntity.ok(valoraciones);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Valoracion>> getValoracionesByUsuario(@PathVariable Long usuarioId) {
        Optional<Usuario> usuario = usuarioService.findUsuarioById(usuarioId);
        if (usuario.isPresent()) {
            List<Valoracion> valoraciones = valoracionService.findByUsuario(usuario.get());
            return ResponseEntity.ok(valoraciones);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Valoracion>> getValoracionesByProducto(@PathVariable Long productoId) {
        Optional<Producto> producto = productoService.findById(productoId);
        if (producto.isPresent()) {
            List<Valoracion> valoraciones = valoracionService.findByProducto(producto.get());
            return ResponseEntity.ok(valoraciones);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Valoracion> createValoracion(@RequestParam Long usuarioId, @RequestParam Long productoId, @RequestBody Valoracion valoracionRequest) {
        Optional<Usuario> usuario = usuarioService.findUsuarioById(usuarioId);
        Optional<Producto> producto = productoService.findById(productoId);

        if (usuario.isPresent() && producto.isPresent()) {
            Valoracion nuevaValoracion = new Valoracion();
            nuevaValoracion.setUsuario(usuario.get());
            nuevaValoracion.setProducto(producto.get());
            nuevaValoracion.setComentario(valoracionRequest.getComentario());
            nuevaValoracion.setCalificacion(valoracionRequest.getCalificacion());
            Valoracion valoracionGuardada = valoracionService.saveValoracion(nuevaValoracion);
            return ResponseEntity.ok(valoracionGuardada);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValoracion(@PathVariable Long id) {
        Optional<Valoracion> valoracion = valoracionService.findById(id); // Buscar por id
        if (valoracion.isPresent()) {
            valoracionService.deleteValoracion(id); // Eliminar la valoración por id
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Valoracion> updateValoracion(
            @PathVariable Long id,
            @RequestParam Long usuarioId,
            @RequestParam Long productoId,
            @RequestBody Valoracion valoracionRequest) {

        Optional<Usuario> usuario = usuarioService.findUsuarioById(usuarioId);
        Optional<Producto> producto = productoService.findById(productoId);
        Optional<Valoracion> valoracionExistente = valoracionService.findById(id);

        if (usuario.isPresent() && producto.isPresent() && valoracionExistente.isPresent()) {
            Valoracion valoracionActualizada = valoracionExistente.get();
            valoracionActualizada.setUsuario(usuario.get());
            valoracionActualizada.setProducto(producto.get());
            valoracionActualizada.setComentario(valoracionRequest.getComentario());
            valoracionActualizada.setCalificacion(valoracionRequest.getCalificacion());

            Valoracion valoracionGuardada = valoracionService.saveValoracion(valoracionActualizada);
            return ResponseEntity.ok(valoracionGuardada);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
