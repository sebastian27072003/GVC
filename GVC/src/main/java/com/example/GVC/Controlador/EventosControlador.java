package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Servicio.EventosServicio;
import com.example.GVC.Servicio.EtiquetaServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class EventosControlador {

    private final EventosServicio eventosServicio;
    private final EtiquetaServicio etiquetaServicio;

    // Constructor para inyectar los servicios
    public EventosControlador(EventosServicio eventosServicio, EtiquetaServicio etiquetaServicio) {
        this.eventosServicio = eventosServicio;
        this.etiquetaServicio = etiquetaServicio;
    }

    // Método GET para listar y filtrar eventos (nombre, campus, facultad)
    @GetMapping("/eventos")
    public String listarEventos(
            @RequestParam(value = "nombre", required = false) String nombreEvento,
            @RequestParam(value = "campus", required = false) String campus,
            @RequestParam(value = "facultad", required = false) String facultad,
            Model model) {

        List<Eventos> eventos;

        if (nombreEvento != null && !nombreEvento.isEmpty()) {
            // Filtrar por nombre del evento
            eventos = eventosServicio.buscarEventosPorNombre(nombreEvento);
        } else if (campus != null && !campus.isEmpty()) {
            // Filtrar por campus y facultad
            if (facultad != null && !facultad.isEmpty()) {
                eventos = eventosServicio.buscarEventosPorCampusYFacultad(campus, facultad);
            } else {
                eventos = eventosServicio.buscarEventosPorCampus(campus);
            }
        } else {
            // Mostrar todos los eventos si no hay filtros
            eventos = eventosServicio.buscarTodosLosEventos();
        }

        model.addAttribute("eventos", eventos);
        return "consultaEventos"; // Muestra la página de consulta de eventos
    }

    // Método POST para guardar un evento
    @PostMapping("/eventos/guardar")
    public String guardarEvento(@ModelAttribute("evento") Eventos evento) {
        System.out.println("Nombre del evento: " + evento.getNomEvento());

        // Guardar el evento en la base de datos sin etiquetas ni imagen
        eventosServicio.guardarEvento(evento);

        // Redirigir después de guardar a la lista de eventos
        return "redirect:/eventos";
    }

    // Método GET para mostrar el formulario de alta de evento
    @GetMapping("/eventos/alta")
    public String mostrarFormularioAltaEvento(Model model) {
        model.addAttribute("evento", new Eventos());
        return "altaEvento"; // Retorna la página de alta de eventos
    }

    // Método GET para filtrar eventos por campus y facultad sin recargar toda la página (uso de fragmentos)
    @GetMapping("/filtrar-eventos")
    public String filtrarEventos(
            @RequestParam(value = "campus", required = false) String campus,
            @RequestParam(value = "facultad", required = false) String facultad,
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

        model.addAttribute("eventos", eventos);
        return "fragments/tablaEventos :: tabla-eventos"; // Fragmento para actualizar solo la tabla
    }
}
