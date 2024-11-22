package com.example.GVC.Servicio;

import com.example.GVC.Modelo.ParticipantesEventos;
import com.example.GVC.Repositorio.ParticipantesEventosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantesEventosServicio {

    @Autowired
    private ParticipantesEventosRepositorio participantesEventosRepositorio;

    // Guardar la relación participante-evento
    public void guardar(ParticipantesEventos participantesEventos) {
        participantesEventosRepositorio.save(participantesEventos);
    }

    // Contar los participantes inscritos en un evento
    public long contarParticipantesPorEvento(Long eventoId) {
        return participantesEventosRepositorio.countByEvento_IdEventos(eventoId);
    }
}
