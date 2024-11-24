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

    // Verificar si un participante está registrado en un evento
    public boolean estaRegistradoEnEvento(Long participanteId, Long eventoId) {
        return participantesEventosRepositorio.existsByParticipante_IdParticipanteAndEvento_IdEventos(participanteId, eventoId);
    }

    // Eliminar registro de un participante en un evento
    public boolean eliminarRegistro(Long participanteId, Long eventoId) {
        ParticipantesEventos registro = participantesEventosRepositorio.findByParticipante_IdParticipanteAndEvento_IdEventos(participanteId, eventoId);
        if (registro != null) {
            participantesEventosRepositorio.delete(registro);
            return true;
        }
        return false;
    }

    // Verificar si existe una relación entre un participante y un evento
    public boolean existeRelacion(Long participanteId, Long eventoId) {
        return participantesEventosRepositorio.existsByParticipante_IdParticipanteAndEvento_IdEventos(participanteId, eventoId);
    }


}