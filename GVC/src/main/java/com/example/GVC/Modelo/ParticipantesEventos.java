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
