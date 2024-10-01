package com.example.primerparcial.favoritos.controllers;

import com.example.primerparcial.favoritos.models.ProductoFavorito;
import com.example.primerparcial.favoritos.services.ProductoFavoritoService;
import com.example.primerparcial.productos.models.Producto;
import com.example.primerparcial.productos.services.ProductoService;
import com.example.primerparcial.usuarios.models.Usuario;
import com.example.primerparcial.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/productos-favoritos")
public class ProductoFavoritoController {

    @Autowired
    private ProductoFavoritoService productoFavoritoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    // Obtener todos los favoritos
    @GetMapping
    public ResponseEntity<List<ProductoFavorito>> getAllFavoritos() {
        List<ProductoFavorito> favoritos = productoFavoritoService.findAllFavoritos();
        return ResponseEntity.ok(favoritos);
    }

    // Obtener favoritos por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ProductoFavorito>> getFavoritosByUsuario(@PathVariable Long usuarioId) {
        Optional<Usuario> usuario = usuarioService.findUsuarioById(usuarioId);
        if (usuario.isPresent()) {
            List<ProductoFavorito> favoritos = productoFavoritoService.findByUsuario(usuario.get());
            return ResponseEntity.ok(favoritos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Agregar favorito
    @PostMapping("/agregar")
    public ResponseEntity<ProductoFavorito> agregarFavorito(@RequestParam Long usuarioId, @RequestParam Long productoId) {
        Optional<Usuario> usuario = usuarioService.findUsuarioById(usuarioId);
        Optional<Producto> producto = productoService.findById(productoId);

        if (usuario.isPresent() && producto.isPresent()) {
            ProductoFavorito nuevoFavorito = new ProductoFavorito();
            nuevoFavorito.setUsuario(usuario.get());
            nuevoFavorito.setProducto(producto.get());
            ProductoFavorito favoritoGuardado = productoFavoritoService.saveProductoFavorito(nuevoFavorito);
            return ResponseEntity.ok(favoritoGuardado);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Eliminar favorito
    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> eliminarFavorito(@RequestParam Long usuarioId, @RequestParam Long productoId) {
        Optional<Usuario> usuario = usuarioService.findUsuarioById(usuarioId);
        Optional<Producto> producto = productoService.findById(productoId);

        if (usuario.isPresent() && producto.isPresent()) {
            productoFavoritoService.deleteByUsuarioAndProducto(usuario.get(), producto.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    // Actualizar un favorito
    @PutMapping("/actualizar")
    public ResponseEntity<ProductoFavorito> actualizarFavorito(@RequestParam Long usuarioId, @RequestParam Long productoId, @RequestParam Long nuevoProductoId) {
        Optional<Usuario> usuario = usuarioService.findUsuarioById(usuarioId);
        Optional<Producto> producto = productoService.findById(productoId);
        Optional<Producto> nuevoProducto = productoService.findById(nuevoProductoId);

        if (usuario.isPresent() && producto.isPresent() && nuevoProducto.isPresent()) {
            Optional<ProductoFavorito> favoritoExistente = productoFavoritoService.findByUsuarioAndProducto(usuario.get(), producto.get());

            if (favoritoExistente.isPresent()) {
                ProductoFavorito favorito = favoritoExistente.get();
                favorito.setProducto(nuevoProducto.get()); // Actualizamos el producto favorito
                ProductoFavorito favoritoActualizado = productoFavoritoService.saveProductoFavorito(favorito);
                return ResponseEntity.ok(favoritoActualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
