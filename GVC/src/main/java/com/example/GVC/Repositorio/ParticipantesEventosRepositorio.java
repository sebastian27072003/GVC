package com.example.GVC.Repositorio;

import com.example.GVC.Modelo.ParticipantesEventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantesEventosRepositorio extends JpaRepository<ParticipantesEventos, Long> {
    // Contar los participantes inscritos en un evento específico
    long countByEvento_IdEventos(Long eventoId);
}
