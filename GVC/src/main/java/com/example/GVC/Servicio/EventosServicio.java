package com.example.GVC.Servicio;

import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Repositorio.EventosRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventosServicio {

    private final EventosRepositorio eventosRepositorio;

    public EventosServicio(EventosRepositorio eventosRepositorio) {
        this.eventosRepositorio = eventosRepositorio;
    }

    public void guardarEvento(Eventos evento) {
        eventosRepositorio.save(evento);
    }

    public List<Eventos> buscarTodosLosEventos() {
        return eventosRepositorio.findAll();
    }
}
