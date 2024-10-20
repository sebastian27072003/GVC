package com.example.GVC.Repositorio;


import com.example.GVC.Modelo.Etiquetas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EtiquetasRepositorio extends JpaRepository<Etiquetas, Long> {
}