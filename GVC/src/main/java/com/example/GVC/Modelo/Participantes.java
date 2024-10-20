package com.example.GVC.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Participantes")
public class Participantes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParticipante;

    private Long Matricula;
    private String Nombre;

    public Long getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Long idParticipante) {
        this.idParticipante = idParticipante;
    }

    public Long getMatricula() {
        return Matricula;
    }

    public void setMatricula(Long matricula) {
        Matricula = matricula;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
