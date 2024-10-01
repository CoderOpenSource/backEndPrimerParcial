package com.example.primerparcial.Reporte.repositories;

import com.example.primerparcial.Reporte.models.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    // Puedes agregar consultas personalizadas si es necesario
}
