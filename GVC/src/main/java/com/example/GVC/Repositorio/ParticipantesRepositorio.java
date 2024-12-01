
package com.example.GVC.Repositorio;

import com.example.GVC.Modelo.Participantes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantesRepositorio extends JpaRepository<Participantes, Long> {
    Participantes findByEmail(String email);  // Buscar por correo electrónico

}
