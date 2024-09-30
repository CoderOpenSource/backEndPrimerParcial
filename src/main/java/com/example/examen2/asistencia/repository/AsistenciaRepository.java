package com.example.examen2.asistencia.repository;

import com.example.examen2.asistencia.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    List<Asistencia> findByEstadoAsistenciaId(Long estadoAsistenciaId);
    @Query("SELECT a FROM Asistencia a WHERE a.docente.id = :docenteId AND a.programacionAcademica.id = :programacionAcademicaId AND FUNCTION('DATE', a.fecha) = FUNCTION('DATE', :fecha)")
    List<Asistencia> findByDocenteAndProgramacionAndDate(@Param("docenteId") Long docenteId, @Param("programacionAcademicaId") Long programacionAcademicaId, @Param("fecha") LocalDateTime fecha);
}
