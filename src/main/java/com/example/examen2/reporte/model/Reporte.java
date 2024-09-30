package com.example.examen2.reporte.model;

import com.example.examen2.asistencia.model.Asistencia;
import com.example.examen2.asistencia.model.Licencia;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaGeneracion;

    private String periodo;

    @ManyToOne
    @JoinColumn(name = "tipo_reporte_id")
    private TipoReporte tipoReporte;

    @ManyToMany
    @JoinTable(
            name = "reporte_asistencia",
            joinColumns = @JoinColumn(name = "reporte_id"),
            inverseJoinColumns = @JoinColumn(name = "asistencia_id")
    )
    private List<Asistencia> asistencias;

    @ManyToMany
    @JoinTable(
            name = "reporte_licencia",
            joinColumns = @JoinColumn(name = "reporte_id"),
            inverseJoinColumns = @JoinColumn(name = "licencia_id")
    )
    private List<Licencia> licencias;

    private String pdfPath;
    private String excelPath;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public TipoReporte getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(TipoReporte tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }

    public List<Licencia> getLicencias() {
        return licencias;
    }

    public void setLicencias(List<Licencia> licencias) {
        this.licencias = licencias;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }
}
