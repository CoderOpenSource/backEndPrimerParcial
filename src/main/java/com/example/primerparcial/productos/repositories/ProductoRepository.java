package com.example.primerparcial.productos.repositories;

import com.example.primerparcial.productos.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {}
