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
    private EtiquetasRepositorio etiquetaRepository;

    @GetMapping("/nueva")
    public String mostrarFormulario() {
        return "formularioEtiqueta";
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
        etiquetaRepository.save(etiqueta);
        model.addAttribute("mensaje", "Etiqueta guardada exitosamente");
        return "formularioEtiqueta";
    }

}