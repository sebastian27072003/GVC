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

    @Column(name = "NomEvento")
    private String nomEvento;

    @Column(name = "Facultad")
    private String facultad;



    @Column(name = "HoraInicio")
    private LocalTime horaInicio;

    @Column(name = "HoraFinal")
    private LocalTime horaFinal;

    @Column(name = "Fecha")
    private LocalDate fecha;

    @Column(name = "Lugar")
    private String lugar;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "Imagen")
    private String imagen;

    @Column(name = "Encargado")
    private String encargado;

    // Aquí está la relación muchos a muchos con Etiquetas
    @ManyToMany
    @JoinTable(
            name = "EventosEtiquetas", // Tabla intermedia para la relación muchos a muchos
            joinColumns = @JoinColumn(name = "idEvento"), // Llave foránea de la tabla "Eventos"
            inverseJoinColumns = @JoinColumn(name = "idEtiqueta") // Llave foránea de la tabla "Etiquetas"
    )
    private List<Etiquetas> etiquetas; // Lista de etiquetas para este evento

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

    public List<Etiquetas> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Etiquetas> etiquetas) {
        this.etiquetas = etiquetas;
    }
}
