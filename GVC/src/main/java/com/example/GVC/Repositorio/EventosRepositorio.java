package com.example.GVC.Repositorio;

import com.example.GVC.Modelo.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventosRepositorio extends JpaRepository<Eventos, Long> {

    // Búsqueda de eventos por nombre (contiene)
    List<Eventos> findByNomEventoContaining(String nombreEvento);

    // Búsqueda de eventos por campus
    List<Eventos> findByCampus(String campus);

    // Búsqueda de eventos por campus y facultad
    List<Eventos> findByCampusAndFacultad(String campus, String facultad);

    // Nueva consulta: Buscar eventos dentro del rango de fechas especificado
    @Query("SELECT e FROM Eventos e WHERE e.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Eventos> findEventosEnRango(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
}
