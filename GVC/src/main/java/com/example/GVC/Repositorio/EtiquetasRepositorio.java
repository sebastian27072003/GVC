package com.example.GVC.Repositorio;


import com.example.GVC.Modelo.Etiquetas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface EtiquetasRepositorio extends JpaRepository<Etiquetas, Long> {
}