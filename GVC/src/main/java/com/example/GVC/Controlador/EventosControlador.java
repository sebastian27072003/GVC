package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Servicio.EventosServicio;
import com.example.GVC.Servicio.EtiquetaServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EventosControlador {
    private final EventosServicio eventosServicio;
    private final EtiquetaServicio etiquetaServicio;

    public EventosControlador(EventosServicio eventosService) {
        this.eventosServicio = eventosService;
        this.etiquetaServicio = etiquetaServicio;
    }


    @GetMapping("/eventos")
    public String eventos(Model model) {
        List<Eventos> eventos = eventosServicio.buscarTodosLosEventos();
        List<Etiquetas> etiquetas = eventosServicio.buscarTodasLasEtiquetas();
        model.addAttribute("eventos", eventos);
        model.addAttribute("etiquetas", etiquetas);
        return "consultaEventos";
    }

    @GetMapping("/filtrar-eventos")
    public String filtrarEventos(
            @RequestParam(value = "campus", required = false) String campus,
            @RequestParam(value = "facultad", required = false) String facultad,
            @RequestParam(value = "etiqueta", required = false) Long etiquetaId,
            @RequestParam(value = "nombreEvento", required = false) String nombreEvento,
            Model model) {

        List<Eventos> eventos;

        if (campus != null && !campus.isEmpty()) {
            if (facultad != null && !facultad.isEmpty()) {
                eventos = eventosServicio.buscarEventosPorCampusYFacultad(campus, facultad);
            } else {
                eventos = eventosServicio.buscarEventosPorCampus(campus);
            }
        } else {
            eventos = eventosServicio.buscarTodosLosEventos();
        }

        if (etiquetaId != null) {
            eventos = eventos.stream()
                    .filter(evento -> evento.getEventosEtiquetas() != null &&
                            evento.getEventosEtiquetas().stream()
                                    .anyMatch(eventosEtiquetas ->
                                            eventosEtiquetas.getEtiqueta().getIdEtiquetas().equals(etiquetaId)))
                    .collect(Collectors.toList());
        }

        if (nombreEvento != null && !nombreEvento.isEmpty()) {
            eventos = eventos.stream()
                    .filter(evento -> evento.getNomEvento().toLowerCase().contains(nombreEvento.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("eventos", eventos);
        return "fragments/tablaEventos :: tabla-eventos";
    }


    @PostMapping("/eventos/guardar")
    public String guardarEvento(@ModelAttribute("evento") Eventos evento) {
        // Guardar el evento en la base de datos sin etiquetas ni imagen
        eventosServicio.guardarEvento(evento);

        return "redirect:/eventos"; // Redirige a la página de consulta de eventos
    }
    @GetMapping("/eventos/alta")
    public String mostrarFormularioAltaEvento(Model model) {
        model.addAttribute("evento", new Eventos()); // Añadimos un nuevo objeto evento
        return "altaEvento"; // Nombre del archivo HTML (altaEvento.html)
    }




}

    @GetMapping("/eventos/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id) {
        eventosServicio.eliminarEvento(id);
        return "redirect:/eventos";
    }
