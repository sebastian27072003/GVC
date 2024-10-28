package com.example.GVC.Controlador;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioControlador {

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        // Validar si oidcUser no es nulo
        if (oidcUser == null) {
            model.addAttribute("nombre", "Invitado");
            model.addAttribute("Email", "No disponible");
            return "home";
        }

        // Extraer nombre y correo de los atributos del usuario
        String nombre = (String) oidcUser.getAttribute("name");
        String email = (String) oidcUser.getAttribute("Email");

        // Agregar datos al modelo
        model.addAttribute("nombre", nombre);
        model.addAttribute("Email", email);

        return "home";
    }

}
