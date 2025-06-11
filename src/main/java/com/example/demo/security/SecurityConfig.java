package com.example.demo.security;

import com.example.demo.filter.JwtAuthenticationFilter;
import org.apache.commons.collections4.Get;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.services.UsuarioDetallesService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@EnableMethodSecurity

@Configuration
public class SecurityConfig {

    private final UsuarioDetallesService usuarioDetallesService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(UsuarioDetallesService usuarioDetallesService, JwtUtil jwtUtil) {
        this.usuarioDetallesService = usuarioDetallesService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioDetallesService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil, usuarioDetallesService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:8080")); //
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))


                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/productos/catalogo").permitAll()
                        .requestMatchers("/api/productos/filtrar").permitAll()
                        .requestMatchers("/api/imagenes/upload").permitAll()
                        .requestMatchers("/api/imagenes/**").permitAll()
                        .requestMatchers("/api/payments/**").permitAll()
                        .requestMatchers("/api/ordenes/crear").permitAll()
                                .requestMatchers("/api/direcciones/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/usuario-direcciones").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/imagenes/dto").permitAll()
                                .requestMatchers(HttpMethod.GET,"api/categorias").permitAll()
                                .requestMatchers(HttpMethod.GET,"api/categorias/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"api/usuarios/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/productos").permitAll()



                                // Rutas solo para ADMIN (crear, eliminar, actualizar productos)
                        .requestMatchers(HttpMethod.POST, "/api/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/talles/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/imagenes/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/categorias/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/{productoId}/detalles").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/**/detalles").hasRole("ADMIN")

                                .requestMatchers("/api/detalle-imagenes/**").hasRole("ADMIN")
                //        .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                //        .requestMatchers("/api/usuarios/cambiar-rol").hasRole("ADMIN")



                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}

