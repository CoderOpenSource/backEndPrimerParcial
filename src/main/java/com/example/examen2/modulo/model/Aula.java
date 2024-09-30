package com.example.examen2.modulo.model;

import jakarta.persistence.*;
import java.util.Set;
import com.example.examen2.programacionacademica.model.ProgramacionAcademica;
import com.example.examen2.carrera.model.Facultad;

@Entity
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private double latitud;  // Coordenada GPS de latitud
    private double longitud; // Coordenada GPS de longitud

    @ManyToOne
    @JoinColumn(name = "facultad_id")
    private Facultad facultad;

    @OneToMany(mappedBy = "aula")
    private Set<ProgramacionAcademica> programaciones;

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

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }

    public Set<ProgramacionAcademica> getProgramaciones() {
        return programaciones;
    }

    public void setProgramaciones(Set<ProgramacionAcademica> programaciones) {
        this.programaciones = programaciones;
    }
}
