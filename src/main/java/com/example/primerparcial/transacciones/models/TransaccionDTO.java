package com.example.primerparcial.transacciones.models;

import java.time.LocalDateTime;

public class TransaccionDTO {

    private Long id;
    private String usuarioNombre;
    private Long carritoId;
    private String tipoPagoNombre;
    private String tipoPagoImagenQr;
    private LocalDateTime fecha;

    public TransaccionDTO(Long id, String usuarioNombre, Long carritoId, String tipoPagoNombre, String tipoPagoImagenQr, LocalDateTime fecha) {
        this.id = id;
        this.usuarioNombre = usuarioNombre;
        this.carritoId = carritoId;
        this.tipoPagoNombre = tipoPagoNombre;
        this.tipoPagoImagenQr = tipoPagoImagenQr;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public Long getCarritoId() {
        return carritoId;
    }

    public void setCarritoId(Long carritoId) {
        this.carritoId = carritoId;
    }

    public String getTipoPagoNombre() {
        return tipoPagoNombre;
    }

    public void setTipoPagoNombre(String tipoPagoNombre) {
        this.tipoPagoNombre = tipoPagoNombre;
    }

    public String getTipoPagoImagenQr() {
        return tipoPagoImagenQr;
    }

    public void setTipoPagoImagenQr(String tipoPagoImagenQr) {
        this.tipoPagoImagenQr = tipoPagoImagenQr;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

