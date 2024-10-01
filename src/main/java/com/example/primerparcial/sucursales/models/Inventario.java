package com.example.primerparcial.sucursales.models;

import com.example.primerparcial.productos.models.ProductoDetalle;
import jakarta.persistence.*;

@Entity
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Sucursal sucursal;

    @ManyToOne
    private ProductoDetalle productodetalle;

    private Integer cantidad;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public ProductoDetalle getProductodetalle() {
        return productodetalle;
    }

    public void setProductodetalle(ProductoDetalle productodetalle) {
        this.productodetalle = productodetalle;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return this.sucursal.getNombre() + " - " + this.productodetalle.getProducto().getNombre() + " - " + this.cantidad;
    }
}

