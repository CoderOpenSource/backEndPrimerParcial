package com.example.examen2.materia.model;

import com.example.examen2.programacionacademica.model.ProgramacionAcademica;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Semestre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int periodo; // 1 o 2
    private int anio; // Año, por ejemplo 2024

    @OneToMany(mappedBy = "semestre")
    @JsonManagedReference
    private Set<ProgramacionAcademica> programaciones = new HashSet<>();

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Set<ProgramacionAcademica> getProgramaciones() {
        return programaciones;
    }

    public void setProgramaciones(Set<ProgramacionAcademica> programaciones) {
        this.programaciones = programaciones;
    }
}
