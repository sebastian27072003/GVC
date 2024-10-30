package com.example.GVC.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "etiquetas")
public class Etiquetas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etiquetas")
    private Long idEtiquetas;

    @Column(name = "nom_etiquetas")
    private String nomEtiquetas;

    @Column(name = "color")
    private String color;

    @Column(name = "descripcion")
    private String descripcion;


    public Long getIdEtiquetas() {
        return idEtiquetas;
    }

    public String getNomEtiquetas() {
        return nomEtiquetas;
    }

    public String getColor() {
        return color;
    }

    public String getDescripcion() {
        return descripcion;
    }


    public void setIdEtiquetas(Long idEtiquetas) {
        this.idEtiquetas = idEtiquetas;
    }

    public void setNomEtiquetas(String nomEtiquetas) {
        this.nomEtiquetas = nomEtiquetas;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}