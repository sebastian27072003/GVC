package com.example.GVC.Modelo;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Eventos")
public class Eventos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEventos")
    private Long idEventos;

    @Column(name = "nom_evento")
    private String nomEvento;

    @Column(name = "facultad")
    private String facultad;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_final")
    private LocalTime horaFinal;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "lugar")
    private String lugar;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "campus")
    private String campus;

    @ManyToMany
    @JoinTable(
            name = "EventosEtiquetas",
            joinColumns = @JoinColumn(name = "idEvento"),
            inverseJoinColumns = @JoinColumn(name = "idEtiqueta")
    )
    private List<Etiquetas> etiquetas;

    // Getters y Setters

    public Long getIdEventos() {
        return idEventos;
    }

    public void setIdEventos(Long idEventos) {
        this.idEventos = idEventos;
    }

    public String getNomEvento() {
        return nomEvento;
    }

    public void setNomEvento(String nomEvento) {
        this.nomEvento = nomEvento;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(LocalTime horaFinal) {
        this.horaFinal = horaFinal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public List<Etiquetas> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Etiquetas> etiquetas) {
        this.etiquetas = etiquetas;
    }
}
