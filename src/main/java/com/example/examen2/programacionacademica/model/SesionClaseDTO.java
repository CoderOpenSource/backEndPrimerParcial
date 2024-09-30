package com.example.examen2.programacionacademica.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public class SesionClaseDTO {
    private Long id;
    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Set<Long> programacionAcademicaIds;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DayOfWeek diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Set<Long> getProgramacionAcademicaIds() {
        return programacionAcademicaIds;
    }

    public void setProgramacionAcademicaIds(Set<Long> programacionAcademicaIds) {
        this.programacionAcademicaIds = programacionAcademicaIds;
    }
}
