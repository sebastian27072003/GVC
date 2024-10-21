package com.example.GVC.Servicio;

import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Repositorio.EventosRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventosServicio {

    private final EventosRepositorio eventosRepositorio;

    public EventosServicio(EventosRepositorio eventosRepository) {
        this.eventosRepositorio = eventosRepository;
    }

    // Método para buscar todos los eventos
    public List<Eventos> buscarTodosLosEventos() {
        return eventosRepositorio.findAll();
    }

    // Método para buscar eventos por nombre
    public List<Eventos> buscarEventosPorNombre(String nombreEvento) {
        return eventosRepositorio.findByNomEventoContaining(nombreEvento);
    }

    // Método para guardar un evento
    public void guardarEvento(Eventos evento) {
        eventosRepositorio.save(evento);
    }
}
