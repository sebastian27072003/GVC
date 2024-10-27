package com.example.GVC.Repositorio;

import com.example.GVC.Modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;



public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
}

