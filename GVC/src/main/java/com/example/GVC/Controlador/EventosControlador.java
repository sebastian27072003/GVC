package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Servicio.EventosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EventosControlador {

    @Autowired
    private EventosServicio eventosServicio;

    // Método GET para mostrar la página de eventos y el formulario de consulta
    @GetMapping("/eventos")
    public String eventosPage(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        String nombre = "Invitado"; // Valor por defecto
        String email = "No disponible"; // Valor por defecto

        if (oidcUser != null) {
            nombre = (String) oidcUser.getAttribute("name");
            email = (String) oidcUser.getAttribute("email");
        }

        model.addAttribute("nombre", nombre);
        model.addAttribute("email", email);
        model.addAttribute("eventos", eventosServicio.buscarTodosLosEventos()); // Lista de eventos

        return "consultaEventos"; // Retorna la página de consulta de eventos
    }

    // Método GET para mostrar el formulario de alta de evento
    @GetMapping("/eventos/alta")
    public String mostrarFormularioAltaEvento(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        String nombre = "Invitado"; // Valor por defecto
        String email = "No disponible"; // Valor por defecto

        if (oidcUser != null) {
            nombre = (String) oidcUser.getAttribute("name");
            email = (String) oidcUser.getAttribute("email");
        }

        model.addAttribute("nombre", nombre);
        model.addAttribute("email", email);
        model.addAttribute("evento", new Eventos()); // Crea un nuevo objeto de Evento para el formulario

        return "altaEvento"; // Retorna la página de alta de eventos
    }

    // Método POST para guardar el evento
    @PostMapping("/eventos/guardar")
    public String guardarEvento(Eventos evento) {
        eventosServicio.guardarEvento(evento); // Guarda el evento en la base de datos
        return "redirect:/eventos"; // Redirige a la página de consulta de eventos
    }

    // Método GET para filtrar eventos
    @GetMapping("/filtrar-eventos")
    public String filtrarEventos(@RequestParam String campus, @RequestParam(required = false) String facultad, Model model) {
        List<Eventos> eventosFiltrados;

        // Filtra los eventos basados en campus y facultad
        if (facultad != null && !facultad.isEmpty()) {
            eventosFiltrados = eventosServicio.buscarEventosPorCampusYFacultad(campus, facultad);
        } else {
            eventosFiltrados = eventosServicio.buscarEventosPorCampus(campus);
        }

        model.addAttribute("eventos", eventosFiltrados); // Agrega la lista de eventos filtrados al modelo
        return "fragments/eventosTabla :: tabla-eventos"; // Asegúrate de que esta ruta sea correcta
    }
}
