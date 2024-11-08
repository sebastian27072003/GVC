package com.example.GVC.Controlador;

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

  public CarouselController(OidcUserService oidcUser, UsuarioServicio usuarioServicio) {
    this.oidcUser = oidcUser;
      this.usuarioServicio = usuarioServicio;
  }

  @GetMapping("/home")
  public String galeria(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
    List<String> imagenes = Arrays.asList(
            "/gpiEvento1.jpg",
            "/ojo2.jpg",
            "/ojo3.jpg"
    );



    String nombre = "Invitado"; // Valor por defecto
    String email = "No disponible"; // Valor por defecto

    if (oidcUser != null) {
      nombre = oidcUser.getAttribute("name");
      email = oidcUser.getAttribute("email");
    }

    String rol = "";
    if (oidcUser != null) {
      rol = usuarioServicio.obtenerRolPorEmail(email); // Metodo para obtener el rol
      System.out.println("Rol recuperado para " + email + ": " + rol);
    }

    // Puedes reemplazar estos valores con los datos del usuario actual
    model.addAttribute("nombre", nombre);
    model.addAttribute("rol", rol);
    model.addAttribute("email", email);

    model.addAttribute("imagenes", imagenes);
    return "home";
  }

  }

