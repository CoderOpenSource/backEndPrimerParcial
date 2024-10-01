package com.example.primerparcial.transacciones.models;

import jakarta.persistence.*;

@Entity
public class TipoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = true)
    private String imagenQr; // URL de la imagen QR

    public TipoPago() {}

    public TipoPago(String nombre, String imagenQr) {
        this.nombre = nombre;
        this.imagenQr = imagenQr;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenQr() {
        return imagenQr;
    }

    public void setImagenQr(String imagenQr) {
        this.imagenQr = imagenQr;
    }
}
