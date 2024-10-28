package com.example.GVC.Servicio;

import com.example.GVC.Modelo.Usuario;
import com.example.GVC.Repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

@Service
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder; // Inyección del PasswordEncoder

    // Constructor para la inyección de dependencias
    @Autowired
    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder; // Asignación
    }
    public String obtenerEmailPorUsername(String username) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByUsername(username);
        return usuarioOptional.map(Usuario::getEmail).orElse("No disponible");
    }

    public boolean validateUser(String username, String password) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByUsername(username);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            // Verificar la contraseña utilizando PasswordEncoder
            return passwordEncoder.matches(password, usuario.getPassword());
        }
        return false; // Si el usuario no existe, retornar false
    }

    public Usuario obtenerORegistrarUsuario(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");

        // Verificar si el usuario ya existe en la base de datos
        Optional<Usuario> usuarioExistente = usuarioRepositorio.findByEmail(email);

        if (usuarioExistente.isPresent()) {
            return usuarioExistente.get();  // Devolver el usuario existente
        } else {
            // Crear un nuevo usuario si no existe
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(oAuth2User.getAttribute("name")); // Suponiendo que el nombre viene de OIDC
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setRol("USER");  // Asignar rol por defecto
            nuevoUsuario.setMatricula(0L);  // Puedes asignar un valor adecuado
            nuevoUsuario.setPassword(passwordEncoder.encode("defaultPassword"));  // Establecer contraseña codificada

            return usuarioRepositorio.save(nuevoUsuario);  // Guardar el usuario en la BD
        }
    }
}
