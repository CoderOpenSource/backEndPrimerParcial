package com.example.primerparcial.productos.models;

import jakarta.persistence.*;

@Entity
public class Modelo3D {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rutaModelo3D;

    @OneToOne
    @JoinColumn(name = "producto_detalle_id")
    private ProductoDetalle productoDetalle;

    // Constructor vacío
    public Modelo3D() {}

    public Modelo3D(String rutaModelo3D, ProductoDetalle productoDetalle) {
        this.rutaModelo3D = rutaModelo3D;
        this.productoDetalle = productoDetalle;
    }

    // Getters y setters
    // ...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRutaModelo3D() {
        return rutaModelo3D;
    }

    public void setRutaModelo3D(String rutaModelo3D) {
        this.rutaModelo3D = rutaModelo3D;
    }

    public ProductoDetalle getProductoDetalle() {
        return productoDetalle;
    }

    public void setProductoDetalle(ProductoDetalle productoDetalle) {
        this.productoDetalle = productoDetalle;
    }
}
