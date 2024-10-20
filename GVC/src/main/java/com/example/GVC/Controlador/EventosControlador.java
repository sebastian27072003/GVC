package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Servicio.EventosServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class EventosControlador {
    private final EventosServicio eventosServicio;

    public EventosControlador(EventosServicio eventosService) {
        this.eventosServicio = eventosService;
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
}
