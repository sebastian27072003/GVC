package com.example.GVC.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "nom_etiquetas", nullable = false)
    private String nomEtiquetas;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany(mappedBy = "etiquetas")
    private List<Eventos> eventos;
}

