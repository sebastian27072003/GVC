package com.example.GVC.Controlador;

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

  public CarouselController(OidcUserService oidcUser) {
    this.oidcUser = oidcUser;
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
    // Puedes reemplazar estos valores con los datos del usuario actual
    model.addAttribute("nombre", nombre);
    model.addAttribute("email", email);

    model.addAttribute("imagenes", imagenes);
    return "home";
  }

  }

