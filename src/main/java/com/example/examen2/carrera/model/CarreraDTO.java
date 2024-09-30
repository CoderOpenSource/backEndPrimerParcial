package com.example.examen2.carrera.model;

import java.util.Set;

public class CarreraDTO {
    private Long id;
    private String nombre;
    private String codigo;
    private FacultadDTO facultad;
    private Set<Long> materiaIds;

    // Getters y setters
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public FacultadDTO getFacultad() {
        return facultad;
    }

    public void setFacultad(FacultadDTO facultad) {
        this.facultad = facultad;
    }

    public Set<Long> getMateriaIds() {
        return materiaIds;
    }

    public void setMateriaIds(Set<Long> materiaIds) {
        this.materiaIds = materiaIds;
    }
}
