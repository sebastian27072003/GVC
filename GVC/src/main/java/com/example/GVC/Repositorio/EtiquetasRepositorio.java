package com.example.GVC.Repositorio;

import com.example.GVC.Modelo.Etiquetas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtiquetasRepositorio extends JpaRepository<Etiquetas, Long> {
    List<Etiquetas> findByNomEtiquetasContainingIgnoreCase(String nombre);

}
