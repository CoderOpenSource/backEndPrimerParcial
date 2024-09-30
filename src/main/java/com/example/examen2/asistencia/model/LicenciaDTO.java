package com.example.examen2.asistencia.model;

import java.util.Date;

public class LicenciaDTO {
    private Long id;
    private Long docenteId;
    private Long programacionAcademicaId;
    private Date fecha;
    private String motivo;
    private String fotoLicencia;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(Long docenteId) {
        this.docenteId = docenteId;
    }

    public Long getProgramacionAcademicaId() {
        return programacionAcademicaId;
    }

    public void setProgramacionAcademicaId(Long programacionAcademicaId) {
        this.programacionAcademicaId = programacionAcademicaId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getFotoLicencia() {
        return fotoLicencia;
    }

    public void setFotoLicencia(String fotoLicencia) {
        this.fotoLicencia = fotoLicencia;
    }
}
