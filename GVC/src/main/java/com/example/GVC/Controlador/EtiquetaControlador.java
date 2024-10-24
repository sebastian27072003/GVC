package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Repositorio.EtiquetasRepositorio;
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
    private EtiquetaServicio etiquetaServicio;

    // Mostrar formulario para crear una nueva etiqueta
    @GetMapping("/etiquetas/crear")
    public String mostrarFormulario(Model model) {
        model.addAttribute("etiqueta", new Etiquetas());
        return "crear_etiqueta";
    }

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
        model.addAttribute("mensaje", "Etiqueta guardada exitosamente");
        return "formularioEtiqueta";
    }

}