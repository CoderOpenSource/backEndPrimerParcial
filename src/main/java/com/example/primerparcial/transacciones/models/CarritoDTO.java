package com.example.primerparcial.transacciones.models;

// CarritoDTO.java

import java.util.List;

public class CarritoDTO {
    private Long id;
    private Long usuarioId; // Solo el ID del usuario
    private boolean disponible;
    private List<CarritoProductoDetalleDTO> productosDetalle; // Lista de detalles del carrito

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public List<CarritoProductoDetalleDTO> getProductosDetalle() {
        return productosDetalle;
    }

    public void setProductosDetalle(List<CarritoProductoDetalleDTO> productosDetalle) {
        this.productosDetalle = productosDetalle;
    }
}

