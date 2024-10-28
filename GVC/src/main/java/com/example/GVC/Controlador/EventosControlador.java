package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Servicio.EventosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
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

    public EventosControlador(EventosServicio eventosService) {
        this.eventosServicio = eventosService;
    }

    // Método GET para listar y filtrar eventos (nombre, campus, facultad)
    @Autowired
    private EventosServicio eventosServicio;

    // Método GET para mostrar la página de eventos y el formulario de consulta
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
            // Mostrar todos los eventos si no hay filtros
            eventos = eventosServicio.buscarTodosLosEventos();
        }
    public String eventosPage(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        String nombre = "Invitado"; // Valor por defecto
        String email = "No disponible"; // Valor por defecto

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

    // Método POST para guardar un evento
    @PostMapping("/eventos/guardar")
    public String guardarEvento(@ModelAttribute("evento") Eventos evento) {
        System.out.println("Nombre del evento: " + evento.getNomEvento());

        // Guardar el evento en la base de datos sin etiquetas ni imagen
        eventosServicio.guardarEvento(evento);

        return "redirect:/eventos"; // Redirige a la página de consulta de eventos
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
    public String mostrarFormularioAltaEvento(Model model) {
        model.addAttribute("evento", new Eventos()); // Añadimos un nuevo objeto evento
        return "altaEvento"; // Nombre del archivo HTML (altaEvento.html)
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

    // Método GET para filtrar eventos por campus y facultad sin recargar toda la página (uso de fragmentos)

    // Método POST para guardar el evento
    @PostMapping("/eventos/guardar")
    public String guardarEvento(Eventos evento) {
        eventosServicio.guardarEvento(evento); // Guarda el evento en la base de datos
        return "redirect:/eventos"; // Redirige a la página de consulta de eventos
    }

    // Método GET para filtrar eventos
    @GetMapping("/filtrar-eventos")
    public String filtrarEventos(
            @RequestParam(value = "campus", required = false) String campus,
            @RequestParam(value = "facultad", required = false) String facultad,
            Model model) {

        List<Eventos> eventos;


}
        // Filtra los eventos basados en campus y facultad
        if (facultad != null && !facultad.isEmpty()) {
            eventosFiltrados = eventosServicio.buscarEventosPorCampusYFacultad(campus, facultad);
        } else {
            eventosFiltrados = eventosServicio.buscarEventosPorCampus(campus);
        }

    @GetMapping("/eventos/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id) {
        eventosServicio.eliminarEvento(id);
        return "redirect:/eventos";
        model.addAttribute("eventos", eventosFiltrados); // Agrega la lista de eventos filtrados al modelo
        return "fragments/eventosTabla :: tabla-eventos"; // Asegúrate de que esta ruta sea correcta
    }
}
