package com.example.primerparcial.transacciones.repositories;

import com.example.primerparcial.transacciones.models.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}

