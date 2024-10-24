package com.example.GVC.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "ParticipantesEventos")
public class ParticipantesEventos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idParticipante")  // Define la columna idParticipante en la tabla
    private Long idParticipante;

    @Column(name = "idEvento")  // Define la columna idEvento en la tabla
    private Long idEvento;

    // Getters y Setters

    public Long getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Long idParticipante) {
        this.idParticipante = idParticipante;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }
}
