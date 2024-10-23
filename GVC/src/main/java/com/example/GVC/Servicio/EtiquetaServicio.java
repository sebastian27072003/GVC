package com.example.GVC.Servicio;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Repositorio.EtiquetasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EtiquetaServicio {

    @Autowired
    private EtiquetasRepositorio etiquetaRepositorio;

    public Etiquetas guardarEtiqueta(Etiquetas etiqueta) {
        return etiquetaRepositorio.save(etiqueta);
    }

    public List<Etiquetas> obtenerEtiquetasPorIds(List<Long> ids) {
        return etiquetaRepositorio.findAllById(ids);
    }
}
