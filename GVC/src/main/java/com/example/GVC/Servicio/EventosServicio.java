package com.example.GVC.Servicio;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Repositorio.EventosRepositorio;
import com.example.GVC.Repositorio.EtiquetasRepositorio;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    // Método para buscar un evento por ID
    public Eventos buscarEventoPorId(Long id) {
        Optional<Eventos> eventoOpt = eventosRepositorio.findById(id);
        return eventoOpt.orElse(null);
    }

    // Método para actualizar un evento existente
    public void actualizarEvento(Long id, Eventos eventoActualizado) {
        Optional<Eventos> eventoExistenteOpt = eventosRepositorio.findById(id);
        if (eventoExistenteOpt.isPresent()) {
            Eventos eventoExistente = eventoExistenteOpt.get();
            // Actualizar los campos con los valores del evento actualizado
            eventoExistente.setNomEvento(eventoActualizado.getNomEvento());
            eventoExistente.setFacultad(eventoActualizado.getFacultad());
            eventoExistente.setHoraInicio(eventoActualizado.getHoraInicio());
            eventoExistente.setHoraFinal(eventoActualizado.getHoraFinal());
            eventoExistente.setFecha(eventoActualizado.getFecha());
            eventoExistente.setLugar(eventoActualizado.getLugar());
            eventoExistente.setDescripcion(eventoActualizado.getDescripcion());
            eventoExistente.setImagen(eventoActualizado.getImagen());
            eventoExistente.setEncargado(eventoActualizado.getEncargado());
            eventoExistente.setCampus(eventoActualizado.getCampus());
            eventoExistente.setestado(eventoActualizado.getestado());
            eventoExistente.setCapacidad(eventoActualizado.getcapacidad());

            // Guardar el evento actualizado
            eventosRepositorio.save(eventoExistente);
        }
    }
}
