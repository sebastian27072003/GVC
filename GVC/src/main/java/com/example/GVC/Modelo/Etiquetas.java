package com.example.GVC.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "etiquetas")
public class Etiquetas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etiquetas")
    private Long idEtiquetas;

    @Column(name = "nom_etiquetas")
    @Size(max = 20)
    private String nomEtiquetas;

    @Size(max = 10)
    @Column(name = "color")
    private String color;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany(mappedBy = "etiquetas")
    @JsonIgnoreProperties("eventos")
    private List<Eventos> eventos;


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

    public List<Eventos> getEventos() {
        return eventos;
    }
}