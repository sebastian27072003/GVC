package com.example.GVC.Repositorio;

import com.example.GVC.Modelo.Participantes;
import com.example.GVC.Modelo.ParticipantesEventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantesEventosRepositorio extends JpaRepository<ParticipantesEventos, Long> {

    // Contar los participantes por evento


    // Buscar la relación participante-evento específica
    ParticipantesEventos findByParticipante_IdParticipanteAndEvento_IdEventos(Long participanteId, Long eventoId);

    boolean existsByParticipante_IdParticipanteAndEvento_IdEventos(Long participanteId, Long eventoId);


    // Contar los participantes inscritos en un evento específico
    long countByEvento_IdEventos(Long eventoId);

    // Obtener la lista de participantes por evento
    List<ParticipantesEventos> findByEvento_IdEventos(Long eventoId);

    // Eliminar la relación participante-evento
    void deleteByParticipante_IdParticipanteAndEvento_IdEventos(Long participanteId, Long eventoId);

    // Verificar si un usuario ya está inscrito en un evento
    boolean existsByEvento_IdEventosAndParticipante_Email(Long eventoId, String email);


}





