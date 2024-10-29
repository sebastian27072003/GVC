package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Servicio.EventosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class EventosControlador {

    private final EventosServicio eventosServicio;

    public EventosControlador(EventosServicio eventosServicio) {
        this.eventosServicio = eventosServicio;
    }

    // Método para mostrar el formulario para crear una nueva etiqueta
    @GetMapping("/usuario-eventos")
    public String mostrarUsuario(@AuthenticationPrincipal OidcUser oidcUser, Model model) {

        String nombre = "Invitado"; // Valor por defecto
        String email = "No disponible"; // Valor por defecto

        if (oidcUser != null) {
            nombre = (String) oidcUser.getAttribute("name");
            email = (String) oidcUser.getAttribute("email");
        }
        // Puedes reemplazar estos valores con los datos del usuario actual
        model.addAttribute("nombre", nombre);
        model.addAttribute("email", email);

        return "consultarEventos"; // Nombre de la vista del formulario
    }

    @GetMapping("/eventos")
    public String eventos(Model model) {
        List<Eventos> eventos = eventosServicio.buscarTodosLosEventos();
        model.addAttribute("eventos", eventos);
        return "consultaEventos";
    }

    @GetMapping("/filtrar-eventos")
    public String filtrarEventos(
            @RequestParam(value = "campus", required = false) String campus,
            @RequestParam(value = "facultad", required = false) String facultad,
            @RequestParam(value = "nombreEvento", required = false) String nombreEvento,
            Model model) {

        List<Eventos> eventos = eventosServicio.filtrarEventos(campus, facultad, nombreEvento);
        model.addAttribute("eventos", eventos);
        return "fragments/tablaEventos :: tabla-eventos";
    }

    @PostMapping("/eventos/guardar")
    public String guardarEvento(@ModelAttribute("evento") Eventos evento) {
        eventosServicio.guardarEvento(evento);
        return "redirect:/eventos";
    }

    @GetMapping("/eventos/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id) {
        eventosServicio.eliminarEvento(id);
        return "redirect:/eventos";
    }

    @GetMapping("/eventos/alta")
    public String mostrarFormularioAltaEvento(Model model) {
        model.addAttribute("evento", new Eventos());
        return "altaEvento";
    }
}
