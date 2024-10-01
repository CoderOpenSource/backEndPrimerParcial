package com.example.primerparcial.productos.models;

import jakarta.persistence.*;

@Entity
public class ImagenProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rutaImagen;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    // Constructor vacío
    public ImagenProducto() {}

    public ImagenProducto(String rutaImagen, Producto producto) {
        this.rutaImagen = rutaImagen;
        this.producto = producto;
    }

    // Getters y setters
    // ...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}

