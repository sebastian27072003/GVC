package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Etiquetas;
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

import java.util.List;

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
    public String guardarEtiqueta(@AuthenticationPrincipal OidcUser oidcUser,@RequestParam String nombre, @RequestParam String color, @RequestParam String descripcion, Model model) {
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

        model.addAttribute("mensaje", "Etiqueta guardada exitosamente");
        model.addAttribute("rol", rol);
        model.addAttribute("nombre", nombre);
        model.addAttribute("nombreusuario", nombreusuario);
        model.addAttribute("email", email);

        return "formularioEtiqueta";
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

        List<Etiquetas> etiquetas = etiquetaServicio.buscarTodasLasEtiquetas();

        model.addAttribute("etiquetas", etiquetas);
        model.addAttribute("nombre", nombre);
        model.addAttribute("rol", rol);
        model.addAttribute("email", email);
        return "consultaEtiquetas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEtiqueta(@PathVariable Long id) {
        etiquetaServicio.eliminarEtiqueta(id);
        return "redirect:/etiquetas/consulta";
    }

    // Método para actualizar una etiqueta existente
    @PostMapping("/editar/{id}")
    public String editarEtiqueta(@PathVariable Long id,
                                 @RequestParam String nombre,
                                 @RequestParam String color,
                                 @RequestParam String descripcion,
                                 Model model) {
        Etiquetas etiquetaExistente = etiquetaServicio.buscarPorId(id);
        if (etiquetaExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Etiqueta no encontrada");
        }

        etiquetaExistente.setNomEtiquetas(nombre);
        etiquetaExistente.setColor(color);
        etiquetaExistente.setDescripcion(descripcion);
        etiquetaServicio.guardarEtiqueta(etiquetaExistente);

        return "redirect:/etiquetas/consulta";
    }





}
