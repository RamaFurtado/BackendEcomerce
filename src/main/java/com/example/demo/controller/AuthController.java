package com.example.demo.controller;

import com.example.demo.dto.*; // Asegúrate de que AuthResponse, LoginRequest, UsuarioRegistroDTO, UsuarioResponseDTO estén aquí
import com.example.demo.enums.Rol; // Asegúrate de que el enum Rol esté importado si lo usas directamente
import com.example.demo.model.Usuario;
import com.example.demo.security.JwtUtil;
import com.example.demo.services.UsuarioDetallesService;
import com.example.demo.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList; // No estoy seguro si ArrayList es necesario aquí, pero lo dejo

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioDetallesService usuarioDetallesService; // Probablemente no lo necesites directamente aquí para 'loadUserByUsername' si 'authenticationManager.authenticate' ya lo hace
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UsuarioDetallesService usuarioDetallesService, // Podrías quitarlo del constructor si no lo usas directamente
                          UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioDetallesService = usuarioDetallesService; // Si no lo usas, quita esta asignación
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            Usuario usuario = usuarioService.buscarPorEmail(loginRequest.getEmail());
            String jwt = jwtUtil.generarToken(usuario);

            // ¡Aquí está la corrección en el orden de los parámetros!
            return ResponseEntity.ok(new AuthResponse(
                    usuario.getId(),            // id (Long)
                    jwt,                        // jwt (String) - ¡Ahora sí en su lugar correcto!
                    usuario.getNombre(),        // nombre (String)
                    usuario.getEmail(),         // email (String) - ¡Ahora en su lugar correcto!
                    usuario.getRol().name(),    // rol (String)
                    usuario.getDni()            // dni (String) - ¡Ahora en su lugar correcto!
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UsuarioRegistroDTO registroDTO) {
        try {
            UsuarioResponseDTO usuarioResponseDTO = usuarioService.registrarUsuario(registroDTO);
            // Después de registrar, necesitas obtener el 'id' y 'rol' del usuario para el AuthResponse completo
            Usuario usuario = usuarioService.buscarPorEmail(usuarioResponseDTO.getEmail()); // Busca la entidad Usuario completa

            String jwt = jwtUtil.generarToken(usuario);

            // Constructor de AuthResponse esperado: (Long id, String nombre, String email, String dni, String rol, String jwt)
            return ResponseEntity.ok(new AuthResponse(
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.getDni(),
                    usuario.getRol().name(),
                    jwt
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el registro: " + e.getMessage());
        }
    }

    //para usar solo los datos del usuario
    @GetMapping("/me")
    public ResponseEntity<?> getUsuarioActual(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }

        String email = userDetails.getUsername();
        Usuario usuario = usuarioService.buscarPorEmail(email);

        UsuarioResponseDTO dto = usuarioService.mapEntityToResponseDto(usuario);
        return ResponseEntity.ok(dto);
    }
}