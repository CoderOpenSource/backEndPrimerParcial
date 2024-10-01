package com.example.primerparcial.favoritos.repositories;

import com.example.primerparcial.favoritos.models.ProductoFavorito;
import com.example.primerparcial.productos.models.Producto;
import com.example.primerparcial.usuarios.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoFavoritoRepository extends JpaRepository<ProductoFavorito, Long> {

    List<ProductoFavorito> findByUsuario(Usuario usuario);

    Optional<ProductoFavorito> findByUsuarioAndProducto(Usuario usuario, Producto producto);

    void deleteByUsuarioAndProducto(Usuario usuario, Producto producto);
}
