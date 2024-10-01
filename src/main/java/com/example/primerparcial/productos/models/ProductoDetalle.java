package com.example.primerparcial.productos.models;

import jakarta.persistence.*;

@Entity
public class ProductoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "tamaño_id")
    private Tamaño tamaño;

    private String imagen2D;

    // Constructor vacío
    public ProductoDetalle() {}

    // Getters y setters
    // ...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Tamaño getTamaño() {
        return tamaño;
    }

    public void setTamaño(Tamaño tamaño) {
        this.tamaño = tamaño;
    }

    public String getImagen2D() {
        return imagen2D;
    }

    public void setImagen2D(String imagen2D) {
        this.imagen2D = imagen2D;
    }
}
