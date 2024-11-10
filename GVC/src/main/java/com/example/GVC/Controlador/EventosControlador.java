
package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Servicio.CloudinaryServicio;
import com.example.GVC.Servicio.EventosServicio;
import com.example.GVC.Servicio.UsuarioServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EventosControlador {

    private final EventosServicio eventosServicio;

    private final UsuarioServicio usuarioServicio;
    private final CloudinaryServicio cloudinaryservicio;

    public EventosControlador(EventosServicio eventosServicio, UsuarioServicio usuarioServicio, CloudinaryServicio cloudinaryservicio) {
        this.eventosServicio = eventosServicio;
        this.usuarioServicio = usuarioServicio;
        this.cloudinaryservicio = cloudinaryservicio;
    }

    // Formulario de alta de eventos con datos del usuario autenticado
    @GetMapping("/eventos/alta")
    public String mostrarFormularioAltaEvento(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        String nombre = oidcUser != null ? oidcUser.getAttribute("name") : "Invitado";
        String email = oidcUser != null ? oidcUser.getAttribute("email") : "No disponible";

        String rol = "";
        if (oidcUser != null) {
            rol = usuarioServicio.obtenerRolPorEmail(email); // Metodo para obtener el rol
            System.out.println("Rol recuperado para " + email + ": " + rol);
        }

        String mensaje = (String) model.asMap().get("mensaje");
        model.addAttribute("mensaje", mensaje);

        List<Etiquetas> etiquetas = eventosServicio.buscarTodasLasEtiquetas(); // Obtener todas las etiquetas disponibles

        model.addAttribute("nombre", nombre);
        model.addAttribute("email", email);
        model.addAttribute("rol", rol);
        model.addAttribute("evento", new Eventos());
        model.addAttribute("etiquetas", etiquetas); // Agregar etiquetas al modelo

        return "altaEvento"; // Vista del formulario de alta de evento
    }

    // Filtrado de eventos basado en varios criterios
    @GetMapping("/filtrar-eventos")
    public String filtrarEventos(@AuthenticationPrincipal OidcUser oidcUser,
                                 @RequestParam(value = "campus", required = false) String campus,
                                 @RequestParam(value = "facultad", required = false) String facultad,
                                 @RequestParam(value = "etiqueta", required = false) Long etiquetaId,
                                 @RequestParam(value = "nombreEvento", required = false) String nombreEvento,

                                 Model model) {

        String nombre = oidcUser != null ? oidcUser.getAttribute("name") : "Invitado";
        String email = oidcUser != null ? oidcUser.getAttribute("email") : "No disponible";

        String rol = "";
        if (oidcUser != null) {
            rol = usuarioServicio.obtenerRolPorEmail(email); // Metodo para obtener el rol
            System.out.println("Rol recuperado para " + email + ": " + rol);
        }

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
                    .filter(evento -> evento.getNomEvento().equalsIgnoreCase(nombreEvento))
                    .collect(Collectors.toList());
        }

        model.addAttribute("eventos", eventos);
        model.addAttribute("rol", rol);
        return "fragments/tablaEventos :: tabla-eventos"; // Fragmento de la tabla con los resultados filtrados
    }

    // Obtener los datos del evento para visualizar
    @GetMapping("/eventos/ver/{id}")
    @ResponseBody
    public ResponseEntity<Eventos> obtenerEventoPorIdParaVer(@PathVariable Long id) {
        try {
            Eventos evento = eventosServicio.buscarEventoPorId(id);
            if (evento == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            System.out.println("Evento a ver: " + evento);
            return ResponseEntity.ok(evento);
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la excepción en los registros del servidor para depurar
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar evento por ID
    @GetMapping("/eventos/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        eventosServicio.eliminarEvento(id);
        redirectAttributes.addFlashAttribute("mensaje", "El evento ha sido eliminado exitosamente.");
        return "redirect:/eventos/consultar"; // Redirige a la lista de eventos tras eliminar
    }

    @PostMapping("/eventos/guardar")
    public String guardarEvento(@ModelAttribute("evento") Eventos evento,
                                @RequestParam List<Long> etiquetasSeleccionadas,
                                @RequestParam("imagenes") MultipartFile imagen,
                                RedirectAttributes redirectAttributes) {
        try {

            if (!imagen.getContentType().startsWith("image/")) {
                redirectAttributes.addFlashAttribute("mensaje", "El archivo subido no es una imagen.");
                return "redirect:/eventos/alta"; // Regresar al formulario con un mensaje de error
            }
            
            // Subir la imagen a Cloudinary
            String imageUrl = cloudinaryservicio.uploadImage(imagen);
            evento.setImagen(imageUrl); // Guarda la URL de la imagen en el evento

            // Aquí van otras operaciones como guardar etiquetas y evento
            List<Etiquetas> etiquetas = eventosServicio.buscarEtiquetasPorIds(etiquetasSeleccionadas);
            evento.setEtiquetas(etiquetas);
            eventosServicio.guardarEvento(evento);

            redirectAttributes.addFlashAttribute("mensaje", "El evento se ha guardado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al subir la imagen.");
        }

        return "redirect:/eventos/alta";

    }

    @GetMapping("/eventos/consultar")
    public String mostrarEventos(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        // Datos del usuario autenticado
        String nombre = oidcUser != null ? oidcUser.getAttribute("name") : "Invitado";
        String email = oidcUser != null ? oidcUser.getAttribute("email") : "No disponible";

        String rol = "";
        if (oidcUser != null) {
            rol = usuarioServicio.obtenerRolPorEmail(email); // Metodo para obtener el rol
            System.out.println("Rol recuperado para " + email + ": " + rol);
        }


        String mensaje = (String) model.asMap().get("mensaje");
        System.out.println("Mensaje flash agregado: " + mensaje);
        model.addAttribute("mensaje", mensaje);

        // Obtener todos los eventos y etiquetas
        List<Eventos> eventos = eventosServicio.buscarTodosLosEventos();
        List<Etiquetas> etiquetas = eventosServicio.buscarTodasLasEtiquetas();

        // Agregar datos al modelo para la vista
        model.addAttribute("nombre", nombre);
        model.addAttribute("email", email);
        model.addAttribute("rol", rol);
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
    public String actualizarEvento(@PathVariable Long id, @ModelAttribute("evento") Eventos eventoActualizado, @RequestParam(required = false) List<Long> etiquetasSeleccionadas,RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Etiquetas seleccionadas: " + etiquetasSeleccionadas);

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
            redirectAttributes.addFlashAttribute("mensaje", "El evento ha sido actualizado exitosamente.");

            return "redirect:/eventos/consultar"; // Redirigir después de guardar
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir error en la consola
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el evento");
        }
    }
}
