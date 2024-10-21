package com.example.GVC.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "ParticipantesEventos")
public class ParticipantesEventos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idParticipante")
    private Long idParticipante;

    @Column(name = "IdEvento")
    private Long IdEvento;


    public Long getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Long idParticipante) {
        this.idParticipante = idParticipante;
    }

    public Long getIdEvento() {
        return IdEvento;
    }

    public void setIdEvento(Long idEvento) {
        IdEvento = idEvento;
    }
}
