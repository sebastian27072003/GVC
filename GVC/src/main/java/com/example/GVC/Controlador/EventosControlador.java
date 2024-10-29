package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Servicio.EventosServicio;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EventosControlador {
    private final EventosServicio eventosServicio;

    public EventosControlador(EventosServicio eventosService) {
        this.eventosServicio = eventosService;
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

    @GetMapping("/eventos/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id) {
        eventosServicio.eliminarEvento(id);
        return "redirect:/eventos";
    }

}