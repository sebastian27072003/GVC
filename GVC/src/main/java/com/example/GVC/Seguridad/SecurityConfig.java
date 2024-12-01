package com.example.GVC.Seguridad;

import com.example.GVC.Modelo.Usuario;
import com.example.GVC.Servicio.UsuarioServicio;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UsuarioServicio usuarioServicio;

    public SecurityConfig(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())  // Desactiva la protección CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/public/**", "/error", "/gpiLogoBonitoUabc.png", "/logout").permitAll() // Permite acceso a la imagen y páginas públicas
                        .requestMatchers("/etiquetas/consulta").hasAnyRole("SUPERADMIN", "ADMIN")
                        .requestMatchers("/etiquetas/nueva").hasRole("SUPERADMIN")
                        .requestMatchers("/eventos/alta").hasAnyRole("SUPERADMIN", "ADMIN")
                        .anyRequest().authenticated() // Cualquier otra solicitud requiere autenticación
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(oidcUserService())
                        )
                        .defaultSuccessUrl("/eventos/consultar", true) // Redirecciona tras el login exitoso
                );

        return http.build();

    }



    @Bean
    public OidcUserService oidcUserService() {
        OidcUserService delegate = new OidcUserService();

        return new OidcUserService() {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest) {
                // Cargamos el usuario de OIDC desde Google
                OidcUser oidcUser = delegate.loadUser(userRequest);

                // Obtenemos o registramos el usuario en la base de datos
                Usuario usuario = usuarioServicio.obtenerORegistrarUsuario(oidcUser);

                // Creamos una autoridad con el rol del usuario desde la base de datos
                Collection<GrantedAuthority> authorities = new ArrayList<>(oidcUser.getAuthorities());
                authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));

                System.out.println("Roles asignados al usuario: " + authorities);

                // Crear un nuevo usuario OIDC con las autoridades agregadas
                return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), "sub");
            }
        };
    }



}