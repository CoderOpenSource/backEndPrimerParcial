package com.example.primerparcial.transacciones.models;

import com.example.primerparcial.productos.models.ProductoDetalle;
import jakarta.persistence.*;

@Entity
public class CarritoProductoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "producto_detalle_id")
    private ProductoDetalle productoDetalle;

    private int cantidad;

    public CarritoProductoDetalle() {}

    public CarritoProductoDetalle(Carrito carrito, ProductoDetalle productoDetalle, int cantidad) {
        this.carrito = carrito;
        this.productoDetalle = productoDetalle;
        this.cantidad = cantidad;

    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public ProductoDetalle getProductoDetalle() {
        return productoDetalle;
    }

    public void setProductoDetalle(ProductoDetalle productoDetalle) {
        this.productoDetalle = productoDetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
