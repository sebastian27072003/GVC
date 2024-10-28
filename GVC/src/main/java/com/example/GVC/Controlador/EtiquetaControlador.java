package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Servicio.EtiquetaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/etiquetas")
public class EtiquetaControlador {

    @Autowired
    private EtiquetaServicio etiquetaServicio;

    // Método para mostrar el formulario para crear una nueva etiqueta
    @GetMapping("/nueva")
    public String mostrarFormulario(@AuthenticationPrincipal OidcUser oidcUser, Model model) {

        String nombre = "Invitado"; // Valor por defecto
        String email = "No disponible"; // Valor por defecto

        if (oidcUser != null) {
            nombre = (String) oidcUser.getAttribute("name");
            email = (String) oidcUser.getAttribute("email");
        }
        // Puedes reemplazar estos valores con los datos del usuario actual
        model.addAttribute("nombre", nombre);
        model.addAttribute("email", email);

        return "formularioEtiqueta"; // Nombre de la vista del formulario
    }

    // Método para guardar la nueva etiqueta
    @PostMapping("/guardar")
    public String guardarEtiqueta(
            @RequestParam String nombre,
            @RequestParam String color,
            @RequestParam String descripcion,
            Model model) {
        Etiquetas etiqueta = new Etiquetas();
        etiqueta.setNomEtiquetas(nombre);
        etiqueta.setColor(color);
        etiqueta.setDescripcion(descripcion);

        etiquetaServicio.guardarEtiqueta(etiqueta);

        // Agregar los atributos al modelo para la vista
        model.addAttribute("mensaje", "Etiqueta guardada exitosamente");
        model.addAttribute("nombre", nombre); // Agregar el nombre del usuario
        model.addAttribute("email", "usuario@example.com"); // Cambia esto por el correo del usuario real

        return "formularioEtiqueta"; // Retorna a la misma vista
    }
}
