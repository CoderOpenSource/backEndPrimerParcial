package com.example.primerparcial.favoritos.models;

import com.example.primerparcial.productos.models.Producto;
import com.example.primerparcial.usuarios.models.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ProductoFavorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    private LocalDateTime fechaAgregado;

    public ProductoFavorito() {
        this.fechaAgregado = LocalDateTime.now(); // Fecha de agregado por defecto
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    @Override
    public String toString() {
        return this.usuario.getNombre() + " - " + this.producto.getNombre() + " - " + this.fechaAgregado;
    }
}
