package com.example.GVC.Servicio;


import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Repositorio.EtiquetasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class EtiquetaServicio {
    @Autowired
    private EtiquetasRepositorio etiquetaRepository;

    public Etiquetas guardarEtiqueta(Etiquetas etiqueta) {
        return etiquetaRepository.save(etiqueta);
    }
}
