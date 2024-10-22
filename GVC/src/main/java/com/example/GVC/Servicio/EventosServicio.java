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

    public List<Eventos> buscarTodosLosEventos() {

        return eventosRepositorio.findAll();
    }

    public List<Eventos> buscarEventosPorNombre(String nombreEvento) {
        return eventosRepositorio.findByNomEventoContaining(nombreEvento);
    }

    public List<Eventos> buscarEventosPorCampus(String campus) {
        return eventosRepositorio.findByCampus(campus);
    }

    public List<Eventos> buscarEventosPorCampusYFacultad(String campus, String facultad) {
        return eventosRepositorio.findByCampusAndFacultad(campus, facultad);
    }

}
