package com.example.examen2.carrera.model;

import java.util.Set;

public class FacultadDTO {
    private Long id;
    private String nombre;
    private Set<Long> carreraIds;

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

    public Set<Long> getCarreraIds() {
        return carreraIds;
    }

    public void setCarreraIds(Set<Long> carreraIds) {
        this.carreraIds = carreraIds;
    }
}

