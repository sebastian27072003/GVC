package com.example.GVC.Repositorio;

import com.example.GVC.Modelo.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventosRepositorio extends JpaRepository<Eventos, Long> {

    // Búsqueda de eventos por nombre (contiene)
    List<Eventos> findByNomEventoContaining(String nombreEvento);

    // Búsqueda de eventos por campus
    List<Eventos> findByCampus(String campus);

    // Búsqueda de eventos por campus y facultad
    List<Eventos> findByCampusAndFacultad(String campus, String facultad);
}
