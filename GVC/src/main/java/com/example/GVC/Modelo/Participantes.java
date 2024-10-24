package com.example.GVC.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Participantes")
public class Participantes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idParticipante")  // Define la columna idParticipante en la tabla
    private Long idParticipante;

    @Column(name = "Matricula")  // Define la columna Matricula en la tabla
    private Long matricula;

    @Column(name = "Nombre")  // Define la columna Nombre en la tabla
    private String nombre;

    // Getters y Setters

    public Long getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Long idParticipante) {
        this.idParticipante = idParticipante;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
