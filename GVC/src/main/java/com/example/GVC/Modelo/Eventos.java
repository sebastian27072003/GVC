package com.example.GVC.Modelo;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.time.LocalDate;

@Entity
@Table(name = "Eventos")
public class Eventos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEventos;

    private String NomEvento;
    private String Facultad;
    private LocalTime HoraInicio;
    private LocalTime HoraFinal;
    private LocalDate Fecha;
    private String Lugar;
    private String Descripcion;
    private String Imagen;
    private String Encargado;

    // Getters and Setters
    public Long getIdEventos() {
        return idEventos;
    }

    public void setIdEventos(Long idEventos) {
        this.idEventos = idEventos;
    }

    public String getNomEvento() {
        return NomEvento;
    }

    public void setNomEvento(String nomEvento) {
        NomEvento = nomEvento;
    }

    public String getFacultad() {
        return Facultad;
    }

    public void setFacultad(String facultad) {
        Facultad = facultad;
    }

    public LocalTime getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        HoraInicio = horaInicio;
    }

    public LocalTime getHoraFinal() {
        return HoraFinal;
    }

    public void setHoraFinal(LocalTime horaFinal) {
        HoraFinal = horaFinal;
    }

    public LocalDate getFecha() {
        return Fecha;
    }

    public void setFecha(LocalDate fecha) {
        Fecha = fecha;
    }

    public String getLugar() {
        return Lugar;
    }

    public void setLugar(String lugar) {
        Lugar = lugar;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getEncargado() {
        return Encargado;
    }

    public void setEncargado(String encargado) {
        Encargado = encargado;
    }
}