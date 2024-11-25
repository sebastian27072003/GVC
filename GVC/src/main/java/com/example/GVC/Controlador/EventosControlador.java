
package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Modelo.Participantes;
import com.example.GVC.Modelo.ParticipantesEventos;
import com.example.GVC.Servicio.*;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class EventosControlador {

    private final EventosServicio eventosServicio;

    private final UsuarioServicio usuarioServicio;
    private final CloudinaryServicio cloudinaryservicio;
    private final ParticipantesServicio participantesServicio;
    private final ParticipantesEventosServicio participantesEventosServicio;
    private final EmailServicio emailServicio;

    public EventosControlador(EventosServicio eventosServicio, UsuarioServicio usuarioServicio, CloudinaryServicio cloudinaryservicio, ParticipantesServicio participantesServicio, ParticipantesEventosServicio participantesEventosServicio, EmailServicio emailServicio) {
        this.eventosServicio = eventosServicio;
        this.usuarioServicio = usuarioServicio;
        this.cloudinaryservicio = cloudinaryservicio;
        this.participantesServicio = participantesServicio;
        this.participantesEventosServicio = participantesEventosServicio;
        this.emailServicio = emailServicio;
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
    public ResponseEntity<Map<String, Object>> obtenerEventoPorIdParaVer(@PathVariable(required = false) Long id, @AuthenticationPrincipal OidcUser oidcUser) {
        Map<String, Object> response = new HashMap<>();

        // Validación del ID
        if (id == null || id <= 0) {
            response.put("success", false);
            response.put("message", "ID del evento no proporcionado o inválido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            // Buscar el evento por su ID
            Eventos evento = eventosServicio.buscarEventoPorId(id);
            if (evento == null) {
                response.put("success", false);
                response.put("message", "Evento no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            String email = oidcUser != null ? oidcUser.getAttribute("email") : null;

            // Verificar si el usuario está registrado
            boolean yaRegistrado = false;
            if (email != null) {
                Participantes participante = participantesServicio.buscarPorEmail(email);
                if (participante != null) {
                    yaRegistrado = participantesEventosServicio.existeRelacion(participante.getIdParticipante(), id);
                }
            }

            response.put("evento", evento);
            response.put("yaRegistrado", yaRegistrado);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Eliminar evento por ID
    @GetMapping("/eventos/eliminar/{id}")
    public String eliminarEvento(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        eventosServicio.eliminarEvento(id);
        redirectAttributes.addFlashAttribute("mensaje", "El evento ha sido eliminado exitosamente.");
        redirectAttributes.addFlashAttribute("tipoMensaje", "exito");
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
                redirectAttributes.addFlashAttribute("tipoMensaje", "error");
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
            redirectAttributes.addFlashAttribute("tipoMensaje", "exito");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al subir la imagen.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
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

        model.addAttribute("eventos", eventos);



        return "consultaEventos"; // Vista para consultar eventos
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
            redirectAttributes.addFlashAttribute("tipoMensaje", "exito");
            return "redirect:/eventos/consultar"; // Redirigir después de guardar
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir error en la consola
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el evento");
        }
    }

    @PostMapping("/eventos/inscribirse")
    public ResponseEntity<Map<String, Object>> inscribirseEvento(@RequestParam(required = false) Long eventoId, @AuthenticationPrincipal OidcUser oidcUser) {
        Map<String, Object> response = new HashMap<>();

        // Validar que el ID del evento no sea nulo
        if (eventoId == null) {
            response.put("success", false);
            response.put("message", "El ID del evento es inválido o no fue proporcionado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Obtener los datos del usuario autenticado
        String nombre = oidcUser != null ? oidcUser.getAttribute("name") : "Invitado";
        String email = oidcUser != null ? oidcUser.getAttribute("email") : "No disponible";

        // Buscar el evento
        Eventos evento = eventosServicio.obtenerEventoPorId(eventoId);
        if (evento == null) {
            response.put("success", false);
            response.put("message", "Evento no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Verificar si hay cupo
        long participantesCount = participantesEventosServicio.contarParticipantesPorEvento(eventoId);
        if (participantesCount >= evento.getCapacidad()) {
            response.put("success", false);
            response.put("message", "No hay espacio disponible en este evento");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Crear o obtener el participante
        Participantes participante = participantesServicio.obtenerOCrearParticipante(email, nombre);

        // Crear la relación Participante-Evento
        ParticipantesEventos participantesEventos = new ParticipantesEventos();
        participantesEventos.setEvento(evento);
        participantesEventos.setParticipante(participante);
        participantesEventos.setNotificaciones(recibirNotificaciones);  // Establecer si el participante quiere recibir notificaciones
        participantesEventos.setRecordatorio(null);     // Valor por defecto

        // Guardar la relación en la base de datos
        participantesEventosServicio.guardarParticipante(participantesEventos);

        // Enviar correo de confirmación solo si el usuario se ha inscrito
        String asunto = "Confirmación de inscripción al evento: " + evento.getNomEvento();
        String mensaje = "Hola " + nombre + ",\n\n"
                + "Te has inscrito exitosamente al evento: " + evento.getNomEvento() + ".\n"
                + "Fecha: " + evento.getFecha() + "\n"
                + "Lugar: " + evento.getLugar() + "\n\n"
                + "Gracias por tu registro.";

        // Enviar correo de confirmación
        emailServicio.enviarCorreo(email, asunto, mensaje);

        // Respuesta exitosa
        response.put("success", true);
        response.put("message", "Inscripción exitosa y correo enviado");
        return ResponseEntity.ok(response);
    }

    // Verificar si el usuario está registrado en el evento
    @GetMapping("/eventos/verificar-registro")
    @ResponseBody
    public ResponseEntity<Boolean> verificarRegistro(@RequestParam Long eventoId, @AuthenticationPrincipal OidcUser oidcUser) {
        String email = oidcUser != null ? oidcUser.getAttribute("email") : null;

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // No autenticado
        }

        Participantes participante = participantesServicio.buscarPorEmail(email);

        if (participante == null) {
            return ResponseEntity.ok(false); // El usuario no tiene un registro como participante
        }

        boolean estaRegistrado = participantesEventosServicio.estaRegistradoEnEvento(participante.getIdParticipante(), eventoId);
        return ResponseEntity.ok(estaRegistrado);
    }

    // Eliminar registro (desinscribirse) del evento
    @DeleteMapping("/eventos/eliminar-registro")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> eliminarRegistro(@RequestParam(required = false) Long eventoId, @AuthenticationPrincipal OidcUser oidcUser) {
        Map<String, Object> response = new HashMap<>();

        // Validar que el ID del evento no sea nulo
        if (eventoId == null) {
            response.put("success", false);
            response.put("message", "El ID del evento es inválido o no fue proporcionado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        String email = oidcUser != null ? oidcUser.getAttribute("email") : null;

        if (email == null) {
            response.put("success", false);
            response.put("message", "Usuario no autenticado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        Participantes participante = participantesServicio.buscarPorEmail(email);

        if (participante == null) {
            response.put("success", false);
            response.put("message", "Participante no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        boolean eliminado = participantesEventosServicio.eliminarRegistro(participante.getIdParticipante(), eventoId);

        if (eliminado) {
            response.put("success", true);
            response.put("message", "Te has desinscrito del evento correctamente");
        } else {
            response.put("success", false);
            response.put("message", "No estás registrado en este evento");
        }

        return ResponseEntity.ok(response);
    }


}
