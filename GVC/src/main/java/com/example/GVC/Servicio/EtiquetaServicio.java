package com.example.GVC.Servicio;

import com.example.GVC.Modelo.Etiquetas;
import com.example.GVC.Modelo.Eventos;
import com.example.GVC.Repositorio.EtiquetasRepositorio;
import com.example.GVC.Repositorio.EventosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtiquetaServicio {

    @Autowired
    private EtiquetasRepositorio etiquetaRepository;
    @Autowired
    private EventosRepositorio eventosRepositorio;

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
        Etiquetas etiqueta = etiquetaRepository.findById(id).orElse(null);
        if (etiqueta != null) {
            // Eliminar las relaciones en la tabla intermedia
            for (Eventos evento : etiqueta.getEventos()) {
                evento.getEtiquetas().remove(etiqueta); // Eliminar la etiqueta de la lista de eventos
            }
            etiquetaRepository.delete(etiqueta); // Finalmente, eliminar la etiqueta
        }
    }

    // Método para verificar si el nombre de la etiqueta es único
    public boolean verificarNombreEtiquetaUnico(String nombre) {
        List<Etiquetas> etiquetaExistente = etiquetaRepository.findByNomEtiquetasIgnoreCase(nombre);
        return etiquetaExistente.isEmpty(); // Devuelve true si no existe una etiqueta con ese nombre
    }

    // Método para buscar una etiqueta por su ID
    public Etiquetas buscarPorId(Long id) {
        Optional<Etiquetas> etiqueta = etiquetaRepository.findById(id);
        return etiqueta.orElse(null);
    }

    public List<Etiquetas>buscarPorNombre(String nombreEtiqueta) {
        return etiquetaRepository.findByNomEtiquetasIgnoreCase(nombreEtiqueta);
    }
}