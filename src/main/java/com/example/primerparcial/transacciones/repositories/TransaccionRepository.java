package com.example.primerparcial.transacciones.repositories;

import com.example.primerparcial.transacciones.models.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
}
