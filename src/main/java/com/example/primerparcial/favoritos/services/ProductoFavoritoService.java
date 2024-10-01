package com.example.primerparcial.favoritos.services;

import com.example.primerparcial.favoritos.models.ProductoFavorito;
import com.example.primerparcial.favoritos.repositories.ProductoFavoritoRepository;
import com.example.primerparcial.productos.models.Producto;
import com.example.primerparcial.usuarios.models.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoFavoritoService {

    @Autowired
    private ProductoFavoritoRepository productoFavoritoRepository;

    public List<ProductoFavorito> findAllFavoritos() {
        return productoFavoritoRepository.findAll();
    }

    public List<ProductoFavorito> findByUsuario(Usuario usuario) {
        return productoFavoritoRepository.findByUsuario(usuario);
    }

    public Optional<ProductoFavorito> findByUsuarioAndProducto(Usuario usuario, Producto producto) {
        return productoFavoritoRepository.findByUsuarioAndProducto(usuario, producto);
    }

    public ProductoFavorito saveProductoFavorito(ProductoFavorito productoFavorito) {
        return productoFavoritoRepository.save(productoFavorito);
    }
    @Transactional
    public void deleteByUsuarioAndProducto(Usuario usuario, Producto producto) {
        productoFavoritoRepository.deleteByUsuarioAndProducto(usuario, producto);
    }
}
