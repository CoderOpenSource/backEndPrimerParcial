package com.example.examen2.asistencia.model;

import java.util.Date;

public class AsistenciaDTO {
    private Long id;
    private Long programacionAcademicaId;
    private Long estadoAsistenciaId;
    private Long docenteId; // Nuevo campo
    private Date fecha;
    private String observaciones;
    private String fotoUrl;
    private double latitud;
    private double longitud;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProgramacionAcademicaId() {
        return programacionAcademicaId;
    }

    public void setProgramacionAcademicaId(Long programacionAcademicaId) {
        this.programacionAcademicaId = programacionAcademicaId;
    }

    public Long getEstadoAsistenciaId() {
        return estadoAsistenciaId;
    }

    public void setEstadoAsistenciaId(Long estadoAsistenciaId) {
        this.estadoAsistenciaId = estadoAsistenciaId;
    }

    public Long getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(Long docenteId) {
        this.docenteId = docenteId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
