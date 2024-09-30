package com.example.examen2.docente.model;

import com.example.examen2.asistencia.model.Asistencia;
import com.example.examen2.common.model.Usuario;
import com.example.examen2.departamento.model.Departamento;
import com.example.examen2.programacionacademica.model.ProgramacionAcademica;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Docente extends Usuario {

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    @ManyToMany
    @JoinTable(
            name = "docente_programacion_academica",
            joinColumns = @JoinColumn(name = "docente_id"),
            inverseJoinColumns = @JoinColumn(name = "programacion_academica_id"))
    private Set<ProgramacionAcademica> programacionesAcademicas;

    @OneToMany(mappedBy = "docente")
    private Set<Asistencia> asistencias;

    // Getters y Setters
    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Set<ProgramacionAcademica> getProgramacionesAcademicas() {
        return programacionesAcademicas;
    }

    public void setProgramacionesAcademicas(Set<ProgramacionAcademica> programacionesAcademicas) {
        this.programacionesAcademicas = programacionesAcademicas;
    }

    public Set<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(Set<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }
}
