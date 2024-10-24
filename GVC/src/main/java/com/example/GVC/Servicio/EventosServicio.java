package com.example.GVC.Servicio;

import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Repositorio.EventosRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventosServicio {

    private final EventosRepositorio eventosRepositorio;

    // Constructor que inyecta el repositorio
    public EventosServicio(EventosRepositorio eventosRepositorio) {
        this.eventosRepositorio = eventosRepositorio;
    }

    // Método para guardar un evento
    public void guardarEvento(Eventos evento) {
        eventosRepositorio.save(evento);
    }

    // Método para buscar todos los eventos
    public List<Eventos> buscarTodosLosEventos() {
        return eventosRepositorio.findAll();
    }

    // Método para buscar eventos por nombre
    public List<Eventos> buscarEventosPorNombre(String nombreEvento) {
        return eventosRepositorio.findByNomEventoContaining(nombreEvento);
    }

    // Método para buscar eventos por campus
    public List<Eventos> buscarEventosPorCampus(String campus) {
        return eventosRepositorio.findByCampus(campus);
    }

    // Método para buscar eventos por campus y facultad
    public List<Eventos> buscarEventosPorCampusYFacultad(String campus, String facultad) {
        return eventosRepositorio.findByCampusAndFacultad(campus, facultad);
    }
}
