package com.example.GVC.Repositorio;

import com.example.GVC.Modelo.ParticipantesEventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantesEventosRepositorio extends JpaRepository<ParticipantesEventos, Long> {


    // Buscar la relación participante-evento específica
    ParticipantesEventos findByParticipante_IdParticipanteAndEvento_IdEventos(Long participanteId, Long eventoId);

    boolean existsByParticipante_IdParticipanteAndEvento_IdEventos(Long participanteId, Long eventoId);


    // Contar los participantes inscritos en un evento específico
    long countByEvento_IdEventos(Long eventoId);





}