package com.example.examen2.docente.model;

import java.util.Set;

public class DocenteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasena; // Nueva propiedad
    private Long rolId; // Nueva propiedad
    private Long departamentoId;
    private Set<Long> programacionAcademicaIds;

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public Long getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Long departamentoId) {
        this.departamentoId = departamentoId;
    }

    public Set<Long> getProgramacionAcademicaIds() {
        return programacionAcademicaIds;
    }

    public void setProgramacionAcademicaIds(Set<Long> programacionAcademicaIds) {
        this.programacionAcademicaIds = programacionAcademicaIds;
    }
}
