package com.example.primerparcial.sucursales.repositories;

import com.example.primerparcial.sucursales.models.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
}

