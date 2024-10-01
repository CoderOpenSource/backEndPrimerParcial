package com.example.primerparcial.productos.repositories;

import com.example.primerparcial.productos.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}

