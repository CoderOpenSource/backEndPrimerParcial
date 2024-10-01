package com.example.primerparcial.transacciones.repositories;

import com.example.primerparcial.transacciones.models.CarritoProductoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoProductoDetalleRepository extends JpaRepository<CarritoProductoDetalle, Long> {
}
