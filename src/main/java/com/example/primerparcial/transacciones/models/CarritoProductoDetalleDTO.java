package com.example.primerparcial.transacciones.models;

public class CarritoProductoDetalleDTO {
    private Long id;
    private Long productoDetalleId; // Solo el ID del producto detalle
    private int cantidad;
    private Long carritoId;

// Getters y Setters
public Long getId() {
    return id;
}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarritoId() {
        return carritoId;
    }

    public void setCarritoId(Long carritoId) {
        this.carritoId = carritoId;
    }

    public Long getProductoDetalleId() {
        return productoDetalleId;
    }

    public void setProductoDetalleId(Long productoDetalleId) {
        this.productoDetalleId = productoDetalleId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}