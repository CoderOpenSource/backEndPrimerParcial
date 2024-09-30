package com.example.examen2.reporte.model;

import java.util.Date;
import java.util.List;

public class ReporteDTO {
    private Long id;
    private Date fechaGeneracion;
    private String periodo;
    private Long tipoReporteId;
    private List<Long> asistenciaIds;
    private List<Long> licenciaIds;
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

    public Long getTipoReporteId() {
        return tipoReporteId;
    }

    public void setTipoReporteId(Long tipoReporteId) {
        this.tipoReporteId = tipoReporteId;
    }

    public List<Long> getAsistenciaIds() {
        return asistenciaIds;
    }

    public void setAsistenciaIds(List<Long> asistenciaIds) {
        this.asistenciaIds = asistenciaIds;
    }

    public List<Long> getLicenciaIds() {
        return licenciaIds;
    }

    public void setLicenciaIds(List<Long> licenciaIds) {
        this.licenciaIds = licenciaIds;
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
