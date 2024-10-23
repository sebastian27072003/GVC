package com.example.GVC.Modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Etiquetas")
public class Etiquetas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtiquetas;

    private String nomEtiquetas;
    private String color;
    private String descripcion;

    @ManyToMany(mappedBy = "etiquetas")
    private List<Eventos> eventos;

    // Getters y Setters
    public Long getIdEtiquetas() {
        return idEtiquetas;
    }

    public void setIdEtiquetas(Long idEtiquetas) {
        this.idEtiquetas = idEtiquetas;
    }

    public String getNomEtiquetas() {
        return nomEtiquetas;
    }

    public void setNomEtiquetas(String nomEtiquetas) {
        this.nomEtiquetas = nomEtiquetas;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Eventos> getEventos() {
        return eventos;
    }

    public void setEventos(List<Eventos> eventos) {
        this.eventos = eventos;
    }
}
