package com.example.examen2.asistencia.repository;

import com.example.examen2.asistencia.model.Licencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LicenciaRepository extends JpaRepository<Licencia, Long> {
    @Query("SELECT l FROM Licencia l WHERE l.docente.id = :docenteId AND FUNCTION('DATE', l.fecha) = FUNCTION('DATE', :fecha)")
    List<Licencia> findByDocenteAndDate(@Param("docenteId") Long docenteId, @Param("fecha") LocalDate fecha);
}