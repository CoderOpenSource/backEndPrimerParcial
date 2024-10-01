package com.example.primerparcial.transacciones.models;

import com.example.primerparcial.usuarios.models.Usuario;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private Set<CarritoProductoDetalle> productosDetalle = new HashSet<>();

    private boolean disponible;

    public Carrito() {}

    public Carrito(Usuario usuario, boolean disponible) {
        this.usuario = usuario;
        this.disponible = disponible;
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

    public Set<CarritoProductoDetalle> getProductosDetalle() {
        return productosDetalle;
    }

    public void setProductosDetalle(Set<CarritoProductoDetalle> productosDetalle) {
        this.productosDetalle = productosDetalle;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}

