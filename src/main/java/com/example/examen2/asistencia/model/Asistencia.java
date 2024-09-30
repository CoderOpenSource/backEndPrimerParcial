package com.example.examen2.asistencia.model;

import com.example.examen2.docente.model.Docente;
import com.example.examen2.programacionacademica.model.ProgramacionAcademica;
import com.example.examen2.reporte.model.Reporte;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "programacion_academica_id")
    private ProgramacionAcademica programacionAcademica;

    @ManyToOne
    @JoinColumn(name = "estado_asistencia_id")
    private EstadoAsistencia estadoAsistencia;

    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Docente docente;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    private String observaciones;
    private String fotoUrl;
    private double latitud;
    private double longitud;

    @ManyToMany(mappedBy = "asistencias")
    private List<Reporte> reportes;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Getters y Setters

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProgramacionAcademica getProgramacionAcademica() {
        return programacionAcademica;
    }

    public void setProgramacionAcademica(ProgramacionAcademica programacionAcademica) {
        this.programacionAcademica = programacionAcademica;
    }

    public EstadoAsistencia getEstadoAsistencia() {
        return estadoAsistencia;
    }

    public void setEstadoAsistencia(EstadoAsistencia estadoAsistencia) {
        this.estadoAsistencia = estadoAsistencia;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
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

    public List<Reporte> getReportes() {
        return reportes;
    }

    public void setReportes(List<Reporte> reportes) {
        this.reportes = reportes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
