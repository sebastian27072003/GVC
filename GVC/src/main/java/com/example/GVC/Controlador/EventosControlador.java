package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Servicio.EventosServicio;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EventosControlador {

    private final EventosServicio eventosServicio;

    public EventosControlador(EventosServicio eventosServicio) {
        this.eventosServicio = eventosServicio;
    }

    // Formulario de alta de eventos con datos del usuario autenticado
    @GetMapping("/eventos/alta")
    public String mostrarFormularioAltaEvento(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        String nombre = oidcUser != null ? oidcUser.getAttribute("name").toString() : "Invitado";
        String email = oidcUser != null ? oidcUser.getAttribute("email").toString() : "No disponible";

        model.addAttribute("nombre", nombre);
        model.addAttribute("email", email);
        model.addAttribute("evento", new Eventos());

        return "altaEvento"; // Vista del formulario de alta de evento
    }

    // Consulta de eventos mostrando nombre y email del usuario autenticado
    @GetMapping("/usuario-eventos")
    public String mostrarUsuario(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        String nombre = oidcUser != null ? oidcUser.getAttribute("name").toString() : "Invitado";
        String email = oidcUser != null ? oidcUser.getAttribute("email").toString() : "No disponible";

        model.addAttribute("nombre", nombre);
        model.addAttribute("email", email);

        return "consultarEventos"; // Vista para consultar eventos
    }

    // Filtrado de eventos basado en varios criterios
    @GetMapping("/filtrar-eventos")
    public String filtrarEventos(
            @RequestParam(value = "campus", required = false) String campus,
            @RequestParam(value = "facultad", required = false) String facultad,
            @RequestParam(value = "etiqueta", required = false) Long etiquetaId,
            @RequestParam(value = "nombreEvento", required = false) String nombreEvento,
            Model model) {

        List<Eventos> eventos = eventosServicio.buscarTodosLosEventos();

        if (campus != null && !campus.isEmpty()) {
            eventos = facultad != null && !facultad.isEmpty()
                    ? eventosServicio.buscarEventosPorCampusYFacultad(campus, facultad)
                    : eventosServicio.buscarEventosPorCampus(campus);
        }

        if (etiquetaId != null) {
            eventos = eventos.stream()
                    .filter(evento -> evento.getEventosEtiquetas() != null &&
                            evento.getEventosEtiquetas().stream()
                                    .anyMatch(etiqueta -> etiqueta.getEtiqueta().getIdEtiquetas().equals(etiquetaId)))
                    .collect(Collectors.toList());
        }

        if (nombreEvento != null && !nombreEvento.isEmpty()) {
            eventos = eventos.stream()
                    .filter(evento -> evento.getNomEvento().toLowerCase().contains(nombreEvento.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("eventos", eventos);
        return "fragments/tablaEventos :: tabla-eventos"; // Fragmento de la tabla con los resultados filtrados
    }

    // Eliminar evento por ID
    @GetMapping("/eventos/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id) {
        eventosServicio.eliminarEvento(id);
        return "redirect:/eventos/consultar"; // Redirige a la lista de eventos tras eliminar
    }

    @PostMapping("/eventos/guardar")
    public String guardarEvento(@ModelAttribute("evento") Eventos evento) {
        System.out.println("Nombre del evento: " + evento.getNomEvento());
        eventosServicio.guardarEvento(evento);
        return "redirect:/eventos";  // Redirige a la página de consulta de eventos después de guardar
    }

    @GetMapping("/eventos/consultar")
    public String mostrarEventos(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        // Datos del usuario autenticado
        String nombre = oidcUser != null ? oidcUser.getAttribute("name").toString() : "Invitado";
        String email = oidcUser != null ? oidcUser.getAttribute("email").toString() : "No disponible";

        // Obtener todos los eventos y etiquetas
        List<Eventos> eventos = eventosServicio.buscarTodosLosEventos();
        List<Etiquetas> etiquetas = eventosServicio.buscarTodasLasEtiquetas();

        // Agregar datos al modelo para la vista
        model.addAttribute("nombre", nombre);
        model.addAttribute("email", email);
        model.addAttribute("eventos", eventos);
        model.addAttribute("etiquetas", etiquetas);

        return "consultaEventos"; // Asegúrate de que esta es la vista correcta
    }

    // Mostrar el formulario de edición del evento
    @GetMapping("/eventos/editar/{id}")
    public String mostrarFormularioEditarEvento(@PathVariable Long id, Model model) {
        Eventos evento = eventosServicio.buscarEventoPorId(id);
        if (evento == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento no encontrado");
        }
        model.addAttribute("evento", evento);
        return "editarEventoModal"; // Vista del modal de edición de evento
    }

    // Guardar cambios en el evento editado
    @PostMapping("/eventos/editar/{id}")
    public String actualizarEvento(@PathVariable Long id, @ModelAttribute("evento") Eventos eventoActualizado) {
        eventosServicio.actualizarEvento(id, eventoActualizado);
        return "redirect:/eventos/consultar"; // Redirigir después de guardar
    }
}
