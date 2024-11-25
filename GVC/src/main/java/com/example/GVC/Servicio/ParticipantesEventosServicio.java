package com.example.GVC.Servicio;

import com.example.GVC.Modelo.ParticipantesEventos;
import com.example.GVC.Repositorio.ParticipantesEventosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // Obtener la lista de participantes de un evento
    public List<ParticipantesEventos> obtenerParticipantesPorEvento(Long eventoId) {
        return participantesEventosRepositorio.findByEvento_IdEventos(eventoId);
    }

    // Eliminar la relación participante-evento
    public void eliminarParticipante(Long participanteId, Long eventoId) {
        participantesEventosRepositorio.deleteByParticipante_IdParticipanteAndEvento_IdEventos(participanteId, eventoId);
    }

    // Verificar si un usuario ya está inscrito en un evento
    public boolean estaInscrito(Long eventoId, String email) {
        return participantesEventosRepositorio.existsByEvento_IdEventosAndParticipante_Email(eventoId, email);
    }

    // Inscribir al usuario en el evento
    public void inscribirUsuario(ParticipantesEventos participantesEventos) {
        participantesEventosRepositorio.save(participantesEventos);
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