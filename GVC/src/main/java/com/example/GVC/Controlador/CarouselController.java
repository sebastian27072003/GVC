package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Servicio.CloudinaryServicio;
import com.example.GVC.Servicio.EventosServicio;
import com.example.GVC.Servicio.UsuarioServicio;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class CarouselController {

  private final OidcUserService oidcUser;
  private final UsuarioServicio usuarioServicio;
  private final EventosServicio eventosServicio;
  private final CloudinaryServicio cloudinaryservicio;

  public CarouselController(OidcUserService oidcUser, UsuarioServicio usuarioServicio, EventosServicio eventosServicio, CloudinaryServicio cloudinaryservicio) {
    this.oidcUser = oidcUser;
    this.usuarioServicio = usuarioServicio;
    this.eventosServicio = eventosServicio;
    this.cloudinaryservicio = cloudinaryservicio;
  }

  @GetMapping("/home")
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

    return "home"; // Vista para consultar eventos
  }
}

