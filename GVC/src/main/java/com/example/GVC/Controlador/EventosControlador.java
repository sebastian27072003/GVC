package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Modelo.Etiquetas;
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
import java.time.LocalTime;
import java.util.List;

@Controller
public class EventosControlador {
    private final EventosServicio eventosServicio;
    private final EtiquetaServicio etiquetaServicio;

    public EventosControlador(EventosServicio eventosService, EtiquetaServicio etiquetaServicio) {
        this.eventosServicio = eventosService;
        this.etiquetaServicio = etiquetaServicio;
    }

    @GetMapping("/eventos")
    public String listarEventos(@RequestParam(value = "nombre", required = false) String nombreEvento, Model model) {
        List<Eventos> eventos;
        if (nombreEvento != null && !nombreEvento.isEmpty()) {
            eventos = eventosServicio.buscarEventosPorNombre(nombreEvento);
        } else {
            eventos = eventosServicio.buscarTodosLosEventos();
        }
        model.addAttribute("eventos", eventos);
        return "consultaEventos";
    }

    // Método para guardar eventos
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
