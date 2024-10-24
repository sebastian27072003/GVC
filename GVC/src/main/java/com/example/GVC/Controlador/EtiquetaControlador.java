package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Servicio.EtiquetaServicio;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EtiquetaServicio etiquetaServicio; // Usamos el servicio para desacoplar la lógica

    // Método GET para mostrar el formulario de creación de etiquetas
    @GetMapping("/nueva")
    public String mostrarFormulario() {
        return "formularioEtiqueta"; // Muestra el formulario HTML
    }

    // Método POST para guardar una nueva etiqueta
    @PostMapping("/guardar")
    public String guardarEtiqueta(
            @RequestParam String nombre,
            @RequestParam String color,
            @RequestParam String descripcion,
            Model model) {

        // Crear un nuevo objeto Etiquetas
        Etiquetas etiqueta = new Etiquetas();
        etiqueta.setNomEtiquetas(nombre);
        etiqueta.setColor(color);
        etiqueta.setDescripcion(descripcion);

        // Guardar la etiqueta a través del servicio
        etiquetaServicio.guardarEtiqueta(etiqueta);

        // Agregar mensaje de éxito
        model.addAttribute("mensaje", "Etiqueta guardada exitosamente");

        // Redirigir al formulario para nuevas entradas
        return "formularioEtiqueta";
    }
}
