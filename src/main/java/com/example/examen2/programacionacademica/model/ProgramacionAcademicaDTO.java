package com.example.examen2.programacionacademica.model;

import java.util.Set;
public class ProgramacionAcademicaDTO {
    private Long id;
    private Long materiaId;
    private Long aulaId;
    private Long semestreId; // Asegúrate de que este campo está aquí
    private Set<Long> docenteIds;
    private Set<Long> sesionClaseIds;
    private String grupo; // Nuevo atributo

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(Long materiaId) {
        this.materiaId = materiaId;
    }

    public Long getAulaId() {
        return aulaId;
    }

    public void setAulaId(Long aulaId) {
        this.aulaId = aulaId;
    }

    public Long getSemestreId() {
        return semestreId;
    }

    public void setSemestreId(Long semestreId) {
        this.semestreId = semestreId;
    }

    public Set<Long> getDocenteIds() {
        return docenteIds;
    }

    public void setDocenteIds(Set<Long> docenteIds) {
        this.docenteIds = docenteIds;
    }

    public Set<Long> getSesionClaseIds() {
        return sesionClaseIds;
    }

    public void setSesionClaseIds(Set<Long> sesionClaseIds) {
        this.sesionClaseIds = sesionClaseIds;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return "ProgramacionAcademicaDTO{" +
                "id=" + id +
                ", materiaId=" + materiaId +
                ", aulaId=" + aulaId +
                ", semestreId=" + semestreId +
                ", docenteIds=" + docenteIds +
                ", sesionClaseIds=" + sesionClaseIds +
                ", grupo='" + grupo + '\'' +
                '}';
    }
}
