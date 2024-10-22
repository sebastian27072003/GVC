package com.example.GVC.Repositorio;

import com.example.GVC.Modelo.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventosRepositorio extends JpaRepository<Eventos, Long> {
    List<Eventos> findByNomEventoContaining(String nombreEvento);

    List<Eventos> findByCampus(String campus);

    List<Eventos> findByCampusAndFacultad(String campus, String facultad);


}
