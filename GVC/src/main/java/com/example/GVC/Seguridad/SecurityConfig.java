package com.example.GVC.Seguridad;

import com.example.GVC.Modelo.Usuario;
import com.example.GVC.Servicio.UsuarioServicio;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
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
                        .requestMatchers("/login", "/public/**", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(oidcUserService())
                        )
                        .defaultSuccessUrl("/eventos/consultar", true)
                );

        return http.build();
    }



    @Bean
    public OidcUserService oidcUserService() {
        OidcUserService delegate = new OidcUserService();

        return new OidcUserService() {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest) {
                // Cargamos el usuario de OIDC
                OidcUser oidcUser = delegate.loadUser(userRequest);

                // Guardamos o recuperamos el usuario desde la base de datos
                Usuario usuario = usuarioServicio.obtenerORegistrarUsuario(oidcUser);

                // Creamos un mapa de atributos combinados
                Map<String, Object> atributos = new HashMap<>(oidcUser.getAttributes());
                atributos.put("nombreUsuario", usuario.getNombre());

                // Retornamos un nuevo DefaultOidcUser con los atributos combinados
                return new DefaultOidcUser(oidcUser.getAuthorities(), oidcUser.getIdToken(), oidcUser.getUserInfo(), "sub");
            }
        };
    }



}
