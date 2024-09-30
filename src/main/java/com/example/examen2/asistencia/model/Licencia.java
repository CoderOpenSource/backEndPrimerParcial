package com.example.examen2.asistencia.model;

import com.example.examen2.docente.model.Docente;
import com.example.examen2.reporte.model.Reporte;
import com.example.examen2.programacionacademica.model.ProgramacionAcademica;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Licencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Docente docente;

    @ManyToOne
    @JoinColumn(name = "programacion_academica_id")
    private ProgramacionAcademica programacionAcademica;

    private Date fecha;
    private String motivo;
    private String fotoLicencia;

    @ManyToMany(mappedBy = "licencias")
    private List<Reporte> reportes;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public ProgramacionAcademica getProgramacionAcademica() {
        return programacionAcademica;
    }

    public void setProgramacionAcademica(ProgramacionAcademica programacionAcademica) {
        this.programacionAcademica = programacionAcademica;
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

    public List<Reporte> getReportes() {
        return reportes;
    }

    public void setReportes(List<Reporte> reportes) {
        this.reportes = reportes;
    }
}
