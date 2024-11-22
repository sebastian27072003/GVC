package com.example.GVC.Servicio;

import com.example.GVC.Modelo.Participantes;
import com.example.GVC.Repositorio.ParticipantesRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantesServicio {

    @Autowired
    private ParticipantesRepositorio participantesRepositorio;

    // Obtener o crear un participante por email
    public Participantes obtenerOCrearParticipante(String email, String nombre) {
        Participantes participante = participantesRepositorio.findByEmail(email);
        if (participante == null) {
            participante = new Participantes();
            participante.setNombre(nombre);
            participante.setEmail(email);
            participantesRepositorio.save(participante);
        }
        return participante;
    }
}
