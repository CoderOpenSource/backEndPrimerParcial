package com.example.primerparcial.transacciones.models;

import java.time.LocalDateTime;

public class FacturaDTO {
    private Long id;
    private Long transaccionId;
    private String urlPdf;
    private LocalDateTime fecha;

    public FacturaDTO(Long id, Long transaccionId, String urlPdf, LocalDateTime fecha) {
        this.id = id;
        this.transaccionId = transaccionId;
        this.urlPdf = urlPdf;
        this.fecha = fecha;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Long transaccionId) {
        this.transaccionId = transaccionId;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

