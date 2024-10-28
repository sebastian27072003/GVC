package com.example.GVC.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "participantes_eventos")
public class ParticipantesEventos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_participante_evento")  // Columna de la clave primaria
    private Long idParticipanteEvento;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)  // Relación con Eventos
    private Eventos evento;

    @Column(name = "nombre_participante", nullable = false)  // Columna de nombre del participante
    private String nombreParticipante;

    // Getters y Setters

    public Long getIdParticipanteEvento() {
        return idParticipanteEvento;
    }

    public void setIdParticipanteEvento(Long idParticipanteEvento) {
        this.idParticipanteEvento = idParticipanteEvento;
    }

    public Eventos getEvento() {
        return evento;
    }

    public void setEvento(Eventos evento) {
        this.evento = evento;
    }

    public String getNombreParticipante() {
        return nombreParticipante;
    }

    public void setNombreParticipante(String nombreParticipante) {
        this.nombreParticipante = nombreParticipante;
    }
}
