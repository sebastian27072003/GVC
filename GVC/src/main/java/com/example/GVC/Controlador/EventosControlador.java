package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Servicio.EventosServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EventosControlador {
    private final EventosServicio eventosServicio;

    public EventosControlador(EventosServicio eventosServicio) {
        this.eventosServicio = eventosServicio;
    }

    @GetMapping("/eventos")
    public String listarEventos(Model model) {
        List<Eventos> eventos = eventosServicio.buscarTodosLosEventos();
        model.addAttribute("eventos", eventos);
        return "consultaEventos";
    }

    @PostMapping("/eventos/guardar")
    public String guardarEvento(@ModelAttribute("evento") Eventos evento) {
        System.out.println("Nombre del evento: " + evento.getNomEvento());
        eventosServicio.guardarEvento(evento);
        return "redirect:/eventos";  // Redirige a la página de consulta de eventos después de guardar
    }

    @GetMapping("/eventos/alta")
    public String mostrarFormularioAltaEvento(Model model) {
        model.addAttribute("evento", new Eventos());
        return "altaEvento";
    }
}
