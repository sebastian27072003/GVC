package com.example.GVC.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Etiquetas")
public class Etiquetas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEtiquetas")
    private Long id;

    @Column(name = "NomEtiquetas", nullable = false)
    private String nombre;

    @Column(name = "Color", nullable = false)
    private String color;
}
