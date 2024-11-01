package com.example.GVC.Servicio;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Repositorio.EtiquetasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtiquetaServicio {

    @Autowired
    private EtiquetasRepositorio etiquetaRepository;

    // Método para guardar una etiqueta
    public Etiquetas guardarEtiqueta(Etiquetas etiqueta) {
        return etiquetaRepository.save(etiqueta);
    }

    // Método para buscar todas las etiquetas
    public List<Etiquetas> buscarTodasLasEtiquetas() {
        return etiquetaRepository.findAll();
    }

    // Método para eliminar una etiqueta por su ID
    public void eliminarEtiqueta(Long id) {
        etiquetaRepository.deleteById(id);
    }

    // Método para buscar una etiqueta por su ID
    public Etiquetas buscarPorId(Long id) {
        Optional<Etiquetas> etiqueta = etiquetaRepository.findById(id);
        return etiqueta.orElse(null);
    }
}
