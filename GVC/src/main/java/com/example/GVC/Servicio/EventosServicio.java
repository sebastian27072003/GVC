package com.example.GVC.Servicio;

import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Repositorio.EventosRepositorio;
import com.example.GVC.Repositorio.EtiquetasRepositorio;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventosServicio {

    private final EventosRepositorio eventosRepositorio;

    public EventosServicio(EventosRepositorio eventosRepositorio) {
        this.eventosRepositorio = eventosRepositorio;
    }

    public List<Eventos> buscarTodosLosEventos() {
        return eventosRepositorio.findAll();
    }

    public List<Eventos> filtrarEventos(String campus, String facultad, String nombreEvento) {
        if (campus != null && !campus.isEmpty() && facultad != null && !facultad.isEmpty()) {
            return eventosRepositorio.findByCampusAndFacultad(campus, facultad);
        } else if (campus != null && !campus.isEmpty()) {
            return eventosRepositorio.findByCampus(campus);
        } else if (nombreEvento != null && !nombreEvento.isEmpty()) {
            return eventosRepositorio.findByNomEventoContaining(nombreEvento);
        }
        return buscarTodosLosEventos();
    }

    public void guardarEvento(Eventos evento) {
        eventosRepositorio.save(evento);
    }

    public void eliminarEvento(Long id) {
        eventosRepositorio.deleteById(id);
    }
}
