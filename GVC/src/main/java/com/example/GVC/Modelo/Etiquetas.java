package com.example.GVC.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Etiquetas")
public class Etiquetas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtiquetas;

    private String NomEtiquetas;
    private String Color;
    private String Descripcion;


    public Long getIdEtiquetas() {
        return idEtiquetas;
    }

    public void setIdEtiquetas(Long idEtiquetas) {
        this.idEtiquetas = idEtiquetas;
    }

    public String getNomEtiquetas() {
        return NomEtiquetas;
    }

    public void setNomEtiquetas(String nomEtiquetas) {
        NomEtiquetas = nomEtiquetas;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}