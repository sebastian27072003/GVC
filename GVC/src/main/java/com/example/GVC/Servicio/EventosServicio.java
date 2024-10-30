
        package com.example.GVC.Servicio;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Repositorio.EventosRepositorio;
import com.example.GVC.Repositorio.EtiquetasRepositorio;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventosServicio {

    private final EventosRepositorio eventosRepositorio;
    private final EtiquetasRepositorio etiquetasRepositorio;


    public EventosServicio(EventosRepositorio eventosRepository, EtiquetasRepositorio etiquetasRepository) {
        this.eventosRepositorio = eventosRepository;
        this.etiquetasRepositorio = etiquetasRepository;
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

    public List<Etiquetas> buscarTodasLasEtiquetas() {
        return etiquetasRepositorio.findAll();
    }
    public void guardarEvento(Eventos evento) {
        eventosRepositorio.save(evento);
    }
    public void eliminarEvento(Long id) {
        eventosRepositorio.deleteById(id);
    }

}