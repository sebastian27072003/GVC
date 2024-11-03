package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Servicio.EventosServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        String nombre = oidcUser != null ? oidcUser.getAttribute("name") : "Invitado";
        String email = oidcUser != null ? oidcUser.getAttribute("email") : "No disponible";

        List<Etiquetas> etiquetas = eventosServicio.buscarTodasLasEtiquetas(); // Obtener todas las etiquetas disponibles

        model.addAttribute("nombre", nombre);
        model.addAttribute("email", email);
        model.addAttribute("evento", new Eventos());
        model.addAttribute("etiquetas", etiquetas); // Agregar etiquetas al modelo

        return "altaEvento"; // Vista del formulario de alta de evento
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
                    .filter(evento -> evento.getEtiquetas() != null &&
                            evento.getEtiquetas().stream()
                                    .anyMatch(etiqueta -> etiqueta.getIdEtiquetas().equals(etiquetaId)))
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
    public String guardarEvento(@ModelAttribute("evento") Eventos evento, @RequestParam List<Long> etiquetasSeleccionadas) {
        List<Etiquetas> etiquetas = eventosServicio.buscarEtiquetasPorIds(etiquetasSeleccionadas); // Obtener etiquetas por IDs
        evento.setEtiquetas(etiquetas); // Asignar etiquetas al evento
        eventosServicio.guardarEvento(evento);
        return "redirect:/eventos/consultar";  // Redirige a la página de consulta de eventos después de guardar
    }

    @GetMapping("/eventos/consultar")
    public String mostrarEventos(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        // Datos del usuario autenticado
        String nombre = oidcUser != null ? oidcUser.getAttribute("name") : "Invitado";
        String email = oidcUser != null ? oidcUser.getAttribute("email") : "No disponible";

        // Obtener todos los eventos y etiquetas
        List<Eventos> eventos = eventosServicio.buscarTodosLosEventos();
        List<Etiquetas> etiquetas = eventosServicio.buscarTodasLasEtiquetas();

        // Agregar datos al modelo para la vista
        model.addAttribute("nombre", nombre);
        model.addAttribute("email", email);
        model.addAttribute("eventos", eventos);
        model.addAttribute("etiquetas", etiquetas);

        return "consultaEventos"; // Vista para consultar eventos
    }

    // Obtener los datos del evento como JSON para la edición
    @GetMapping("/eventos/editar/{id}")
    @ResponseBody
    public ResponseEntity<Eventos> obtenerEventoPorId(@PathVariable Long id) {
        try {
            Eventos evento = eventosServicio.buscarEventoPorId(id);
            if (evento == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            System.out.println("Evento a editar: " + evento);
            return ResponseEntity.ok(evento);
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la excepción en los registros del servidor para depurar
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Guardar cambios en el evento editado
    @PostMapping("/eventos/editar/{id}")
    public String actualizarEvento(@PathVariable Long id, @ModelAttribute("evento") Eventos eventoActualizado, @RequestParam(required = false) List<Long> etiquetasSeleccionadas) {
        try {
            Eventos eventoExistente = eventosServicio.buscarEventoPorId(id);
            if (eventoExistente == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento no encontrado");
            }


            // Actualizar los datos del evento existente
            eventoExistente.setNomEvento(eventoActualizado.getNomEvento());
            eventoExistente.setCampus(eventoActualizado.getCampus());
            eventoExistente.setFacultad(eventoActualizado.getFacultad());
            eventoExistente.setFecha(eventoActualizado.getFecha());
            eventoExistente.setLugar(eventoActualizado.getLugar());
            eventoExistente.setDescripcion(eventoActualizado.getDescripcion());
            eventoExistente.setEstado(eventoActualizado.getEstado());
            eventoExistente.setHoraInicio(eventoActualizado.getHoraInicio());
            eventoExistente.setHoraFinal(eventoActualizado.getHoraFinal());
            eventoExistente.setEncargado(eventoActualizado.getEncargado());
            eventoExistente.setCapacidad(eventoActualizado.getCapacidad());

            // Asignar etiquetas si se proporcionan
            if (etiquetasSeleccionadas != null) {
                List<Etiquetas> etiquetas = eventosServicio.buscarEtiquetasPorIds(etiquetasSeleccionadas);
                eventoExistente.setEtiquetas(etiquetas);
            }

            // Guardar el evento actualizado
            eventosServicio.actualizarEvento(id, eventoExistente);

            return "redirect:/eventos/consultar"; // Redirigir después de guardar
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir error en la consola
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el evento");
        }
    }
}
