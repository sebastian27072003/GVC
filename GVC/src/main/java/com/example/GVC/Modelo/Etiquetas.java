package com.example.GVC.Modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Etiquetas")
public class Etiquetas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEtiquetas")  // Nombre de la columna en la base de datos
    private Long idEtiquetas;

    @Column(name = "NomEtiquetas")  // Nombre de la columna en la base de datos
    private String nomEtiquetas;

    @Column(name = "Color")  // Nombre de la columna en la base de datos
    private String color;

    @Column(name = "Descripcion")  // Nombre de la columna en la base de datos
    private String descripcion;

    // Relación muchos a muchos con la clase "Eventos"
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
