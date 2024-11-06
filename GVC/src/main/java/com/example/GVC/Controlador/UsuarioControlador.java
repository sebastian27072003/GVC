package com.example.GVC.Controlador;

import com.example.GVC.Servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioControlador {

    UsuarioServicio usuarioServicio;

    @Autowired
    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        if (oidcUser == null) {
            model.addAttribute("nombre", "Invitado");
            model.addAttribute("email", "No disponible");
            model.addAttribute("rol", "USER");
            return "home";
        }

        String nombre = (String) oidcUser.getAttribute("name");
        String email = (String) oidcUser.getAttribute("email");
        String rol = usuarioServicio.obtenerRolPorEmail(email);

        model.addAttribute("nombre", nombre);
        model.addAttribute("email", email);
        model.addAttribute("rol", rol);

        System.out.println("Rol obtenido: " + rol);

        return "home";
    }

}
