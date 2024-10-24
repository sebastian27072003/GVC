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
    @Column(name = "idEventos")  // Nombre de la columna en la base de datos
    private Long idEventos;

    @Column(name = "nom_evento")  // Nombre de la columna en la base de datos
    private String nomEvento;

    @Column(name = "facultad")  // Nombre de la columna en la base de datos
    private String facultad;

    @Column(name = "hora_inicio")  // Nombre de la columna en la base de datos
    private LocalTime horaInicio;

    @Column(name = "hora_final")  // Nombre de la columna en la base de datos
    private LocalTime horaFinal;

    @Column(name = "fecha")  // Nombre de la columna en la base de datos
    private LocalDate fecha;

    @Column(name = "lugar")  // Nombre de la columna en la base de datos
    private String lugar;

    @Column(name = "descripcion")  // Nombre de la columna en la base de datos
    private String descripcion;

    @Column(name = "imagen")  // Nombre de la columna en la base de datos
    private String imagen;

    @Column(name = "encargado")  // Nombre de la columna en la base de datos
    private String encargado;

    @Column(name = "campus")  // Nombre de la columna en la base de datos
    private String campus;

    // Relación muchos a muchos con Etiquetas
    @ManyToMany
    @JoinTable(
            name = "EventosEtiquetas",  // Tabla intermedia para la relación muchos a muchos
            joinColumns = @JoinColumn(name = "idEvento"),  // Llave foránea de la tabla "Eventos"
            inverseJoinColumns = @JoinColumn(name = "idEtiqueta")  // Llave foránea de la tabla "Etiquetas"
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
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
