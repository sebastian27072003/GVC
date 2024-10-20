package com.example.GVC.Controlador;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Repositorio.EtiquetasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EtiquetaController {

    @Autowired
    private EtiquetasRepositorio etiquetasRepositorio;

    // Mostrar formulario para crear una nueva etiqueta
    @GetMapping("/etiquetas/crear")
    public String mostrarFormulario(Model model) {
        model.addAttribute("etiqueta", new Etiquetas());
        return "crear_etiqueta";
    }

    // Guardar la etiqueta en la base de datos
    @PostMapping("/etiquetas/guardar")
    public String guardarEtiqueta(@ModelAttribute("etiqueta") Etiquetas etiqueta) {
        etiquetasRepositorio.save(etiqueta);
        return "redirect:/etiquetas/crear?exito";
    }
}
