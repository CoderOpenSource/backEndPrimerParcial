package com.example.primerparcial.transacciones.repositories;

import com.example.primerparcial.transacciones.models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    // Encuentra todas las facturas que estén asociadas con una transacción específica
    List<Factura> findByTransaccionId(Long transaccionId);
}
