package com.example.GVC.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "participantes_eventos")
public class ParticipantesEventos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_participante_evento")
    private Long idParticipanteEvento;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Eventos evento;

    @ManyToOne
    @JoinColumn(name = "id_participante", nullable = false)
    private Participantes participante;

    @Column(name = "notificaciones")
    private Boolean notificaciones;

    @Column(name = "recordatorio")
    private String recordatorio;


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

    public Participantes getParticipante() {
        return participante;
    }

    public void setParticipante(Participantes participante) {
        this.participante = participante;
    }

    public Boolean getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(Boolean notificaciones) {
        this.notificaciones = notificaciones;
    }

    public String getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(String recordatorio) {
        this.recordatorio = recordatorio;
    }
}
