package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Servicio.EtiquetaServicio;
import com.example.GVC.Servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/etiquetas")
public class EtiquetaControlador {

    private final UsuarioServicio usuarioServicio;

    @Autowired
    private EtiquetaServicio etiquetaServicio;

    public EtiquetaControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    // Método para mostrar el formulario para crear una nueva etiqueta
    @GetMapping("/nueva")
    public String mostrarFormulario(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        String nombre = oidcUser != null ? oidcUser.getAttribute("name").toString() : "Invitado";
        String email = oidcUser != null ? oidcUser.getAttribute("email").toString() : "No disponible";

        String rol = "";
        if (oidcUser != null) {
            rol = usuarioServicio.obtenerRolPorEmail(email); // Metodo para obtener el rol
            System.out.println("Rol recuperado para " + email + ": " + rol);
        }

        model.addAttribute("nombreusuario", nombre);
        model.addAttribute("email", email);
        model.addAttribute("rol", rol);

        return "formularioEtiqueta";
    }

    // Método para guardar la nueva etiqueta
    @PostMapping("/guardar")
    public String guardarEtiqueta(@AuthenticationPrincipal OidcUser oidcUser,
                                  @RequestParam String nombre,
                                  @RequestParam String color,
                                  @RequestParam String descripcion,
                                  RedirectAttributes redirectAttributes) {

        // Verificar si el color es hexadecimal válido
        if (!esColorHexadecimalValido(color)) {
            redirectAttributes.addFlashAttribute("mensaje", "El color debe ser un valor hexadecimal válido.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
            return "formularioEtiqueta";
        }

        // Verificar si el nombre de la etiqueta ya está en uso
        if (!etiquetaServicio.verificarNombreEtiquetaUnico(nombre)) {
            redirectAttributes.addFlashAttribute("mensaje", "El nombre de la etiqueta ya existe.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
            return "redirect:/etiquetas/nueva";
        }

        // Guardar la nueva etiqueta si pasa todas las validaciones
        Etiquetas etiqueta = new Etiquetas();
        etiqueta.setNomEtiquetas(nombre);
        etiqueta.setColor(color);
        etiqueta.setDescripcion(descripcion);

        String nombreusuario = oidcUser != null ? oidcUser.getAttribute("name").toString() : "Invitado";
        String email = oidcUser != null ? oidcUser.getAttribute("email").toString() : "No disponible";

        String rol = "";
        if (oidcUser != null) {
            rol = usuarioServicio.obtenerRolPorEmail(email); // Metodo para obtener el rol
            System.out.println("Rol recuperado para " + email + ": " + rol);
        }

        etiquetaServicio.guardarEtiqueta(etiqueta);

        redirectAttributes.addFlashAttribute("mensaje", "La etiqueta se guardó exitosamente.");
        redirectAttributes.addFlashAttribute("tipoMensaje", "exito");
        return "redirect:/etiquetas/nueva";
    }

    // Función para validar si el color es un valor hexadecimal
    private boolean esColorHexadecimalValido(String color) {
        // Expresión regular para validar el formato hexadecimal (#RRGGBB o #RGB)
        return color.matches("^#([0-9a-fA-F]{3}){1,2}$");
    }

    @GetMapping("/validarNombre")
    @ResponseBody
    public boolean validarNombreEtiqueta(@RequestParam String nombre) {
        return etiquetaServicio.verificarNombreEtiquetaUnico(nombre);
    }

    @GetMapping("/consulta")
    public String mostrarEtiquetas(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        String nombre = oidcUser != null ? oidcUser.getAttribute("name").toString() : "Invitado";
        String email = oidcUser != null ? oidcUser.getAttribute("email").toString() : "No disponible";

        String rol = "";
        if (oidcUser != null) {
            rol = usuarioServicio.obtenerRolPorEmail(email); // Metodo para obtener el rol
            System.out.println("Rol recuperado para " + email + ": " + rol);
        }

        String mensaje = (String) model.asMap().get("mensaje");
        System.out.println("Mensaje flash agregado: " + mensaje);
        model.addAttribute("mensaje", mensaje);

        List<Etiquetas> etiquetas = etiquetaServicio.buscarTodasLasEtiquetas();

        model.addAttribute("etiquetas", etiquetas);
        model.addAttribute("nombre", nombre);
        model.addAttribute("rol", rol);
        model.addAttribute("email", email);
        return "consultaEtiquetas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEtiqueta(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        etiquetaServicio.eliminarEtiqueta(id);
        redirectAttributes.addFlashAttribute("mensaje", "La etiqueta ha sido eliminada exitosamente.");
        redirectAttributes.addFlashAttribute("tipoMensaje", "exito");

        return "redirect:/etiquetas/consulta";
    }

    // Método para actualizar una etiqueta existente
    @PostMapping("/editar/{id}")
    public String editarEtiqueta(@PathVariable Long id,
                                 @RequestParam String nombre,
                                 @RequestParam String color,
                                 @RequestParam String descripcion,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        Etiquetas etiquetaExistente = etiquetaServicio.buscarPorId(id);
        if (etiquetaExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Etiqueta no encontrada");
        }

        etiquetaExistente.setNomEtiquetas(nombre);
        etiquetaExistente.setColor(color);
        etiquetaExistente.setDescripcion(descripcion);

        etiquetaServicio.guardarEtiqueta(etiquetaExistente);
        redirectAttributes.addFlashAttribute("mensaje", "La etiqueta ha sido actualizada exitosamente.");
        redirectAttributes.addFlashAttribute("tipoMensaje", "exito");
        return "redirect:/etiquetas/consulta";
    }


    @GetMapping("/filtrar")
    public String filtrarEtiquetas(@AuthenticationPrincipal OidcUser oidcUser,@RequestParam("nombreEtiqueta") String nombreEtiqueta, Model model) {
        String nombre = oidcUser != null ? oidcUser.getAttribute("name") : "Invitado";
        String email = oidcUser != null ? oidcUser.getAttribute("email") : "No disponible";


        String rol = "";

        if (oidcUser != null) {
            rol = usuarioServicio.obtenerRolPorEmail(email); // Metodo para obtener el rol
            System.out.println("Rol recuperado para " + email + ": " + rol);
        }

        List<Etiquetas> etiquetas = (nombreEtiqueta == null || nombreEtiqueta.trim().isEmpty())
                ? etiquetaServicio.buscarTodasLasEtiquetas() // Método para obtener todas las etiquetas
                : etiquetaServicio.buscarPorNombre(nombreEtiqueta);


        model.addAttribute("rol", rol);
        model.addAttribute("etiquetas", etiquetas);
        return "fragments/tablaetiquetas :: tabla-etiquetas";
    }


}
