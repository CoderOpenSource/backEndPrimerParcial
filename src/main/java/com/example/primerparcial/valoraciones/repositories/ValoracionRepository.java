package com.example.primerparcial.valoraciones.repositories;

import com.example.primerparcial.productos.models.Producto;
import com.example.primerparcial.usuarios.models.Usuario;
import com.example.primerparcial.valoraciones.models.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {

    List<Valoracion> findByUsuario(Usuario usuario);

    List<Valoracion> findByProducto(Producto producto);

    Optional<Valoracion> findByUsuarioAndProducto(Usuario usuario, Producto producto);
}

