package com.example.GVC.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "ParticipantesEventos")
public class ParticipantesEventos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idParticipante", nullable = false)
    private Participantes participante;

    @ManyToOne
    @JoinColumn(name = "idEvento", nullable = false)
    private Eventos evento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Participantes getParticipante() {
        return participante;
    }

    public void setParticipante(Participantes participante) {
        this.participante = participante;
    }

    public Eventos getEvento() {
        return evento;
    }

    public void setEvento(Eventos evento) {
        this.evento = evento;
    }
}
