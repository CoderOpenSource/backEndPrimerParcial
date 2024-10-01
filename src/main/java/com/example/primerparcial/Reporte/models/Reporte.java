package com.example.primerparcial.Reporte.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "url_pdf")
    private String urlPdf;

    @Column(name = "url_excel")
    private String urlExcel;

    // Constructores
    public Reporte() {}

    public Reporte(LocalDateTime fecha, String urlPdf, String urlExcel) {
        this.fecha = fecha;
        this.urlPdf = urlPdf;
        this.urlExcel = urlExcel;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    public String getUrlExcel() {
        return urlExcel;
    }

    public void setUrlExcel(String urlExcel) {
        this.urlExcel = urlExcel;
    }
}
