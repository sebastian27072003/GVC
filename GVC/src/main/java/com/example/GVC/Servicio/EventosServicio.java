package com.example.GVC.Servicio;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Repositorio.EventosRepositorio;
import com.example.GVC.Repositorio.EtiquetasRepositorio;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventosServicio {

    private final EventosRepositorio eventosRepositorio;
    private final EtiquetasRepositorio etiquetasRepositorio;

    public EventosServicio(EventosRepositorio eventosRepositorio, EtiquetasRepositorio etiquetasRepositorio) {
        this.eventosRepositorio = eventosRepositorio;
        this.etiquetasRepositorio = etiquetasRepositorio;
    }

    // Buscar todos los eventos
    public List<Eventos> buscarTodosLosEventos() {
        return eventosRepositorio.findAll();
    }

    // Buscar eventos por nombre
    public List<Eventos> buscarEventosPorNombre(String nombreEvento) {
        return eventosRepositorio.findByNomEventoContaining(nombreEvento);
    }

    // Buscar eventos por campus
    public List<Eventos> buscarEventosPorCampus(String campus) {
        return eventosRepositorio.findByCampus(campus);
    }

    // Buscar eventos por campus y facultad
    public List<Eventos> buscarEventosPorCampusYFacultad(String campus, String facultad) {
        return eventosRepositorio.findByCampusAndFacultad(campus, facultad);
    }

    // Buscar todas las etiquetas disponibles
    public List<Etiquetas> buscarTodasLasEtiquetas() {
        return etiquetasRepositorio.findAll();
    }

    // Guardar un nuevo evento
    public void guardarEvento(Eventos evento) {
        eventosRepositorio.save(evento);
    }

    // Eliminar un evento por ID
    public void eliminarEvento(Long id) {
        eventosRepositorio.deleteById(id);
    }

    // Buscar un evento por ID
    public Eventos buscarEventoPorId(Long id) {
        Optional<Eventos> eventoOpt = eventosRepositorio.findById(id);
        return eventoOpt.orElse(null);
    }

    // Método para actualizar un evento existente
    public void actualizarEvento(Long id, Eventos eventoActualizado) {
        Optional<Eventos> eventoExistenteOpt = eventosRepositorio.findById(id);

        if (eventoExistenteOpt.isPresent()) {
            Eventos eventoExistente = eventoExistenteOpt.get();

            System.out.println("Hora final recibida: " + eventoActualizado.getHoraFinal());
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
            eventoExistente.setEstado(eventoActualizado.getEstado());
            eventoExistente.setCapacidad(eventoActualizado.getCapacidad());

            // Actualizar etiquetas (si el evento actualizado tiene etiquetas)
            if (eventoActualizado.getEtiquetas() != null) {
                List<Etiquetas> etiquetas = etiquetasRepositorio.findAllById(
                        eventoActualizado.getEtiquetas().stream()
                                .map(Etiquetas::getIdEtiquetas)
                                .collect(Collectors.toList())
                );
                eventoExistente.setEtiquetas(etiquetas);
            }

            // Guardar el evento actualizado
            eventosRepositorio.save(eventoExistente);
        }
    }

    // Buscar etiquetas por una lista de IDs
    public List<Etiquetas> buscarEtiquetasPorIds(List<Long> ids) {
        return etiquetasRepositorio.findAllById(ids);
    }
}
