package com.example.primerparcial.sucursales.repositories;

import com.example.primerparcial.productos.models.ProductoDetalle;
import com.example.primerparcial.sucursales.models.Inventario;
import com.example.primerparcial.sucursales.models.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    // Buscar inventario por sucursal
    List<Inventario> findBySucursal(Sucursal sucursal);

    // Buscar inventario por ProductoDetalle
    Optional<Inventario> findByProductodetalle(ProductoDetalle productoDetalle);
}
