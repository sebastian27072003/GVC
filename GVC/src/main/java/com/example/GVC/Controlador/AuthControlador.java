package com.example.GVC.Controlador;

import com.example.GVC.Servicio.UsuarioServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthControlador {

    private final UsuarioServicio userService; // Asegúrate de que tengas un servicio de usuario inyectado

    public AuthControlador(UsuarioServicio userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        // Validar credenciales utilizando el método correcto
        if (userService.validateUser(username, password)) {
            // Redirigir a la página de alta de eventos
            return "redirect:/eventos";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login"; // Retornar a la página de login
        }
    }
}
