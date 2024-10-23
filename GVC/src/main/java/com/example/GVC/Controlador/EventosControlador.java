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
    public String eventos(Model model) {
        List<Eventos> eventos = eventosServicio.buscarTodosLosEventos(); // O el método que necesites
        model.addAttribute("eventos", eventos);
        return "consultaEventos";
    }

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
        return "fragments/tablaEventos :: tabla-eventos";
    }
}



