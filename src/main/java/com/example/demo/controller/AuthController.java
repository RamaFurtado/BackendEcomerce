package com.example.demo.controller;

import com.example.demo.dto.*;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioDetallesService usuarioDetallesService;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UsuarioDetallesService usuarioDetallesService,
                          UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioDetallesService = usuarioDetallesService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            UserDetails userDetails = usuarioDetallesService.loadUserByUsername(loginRequest.getEmail());
            String jwt = jwtUtil.generarToken(userDetails.getUsername());

            //para enviar los datos al front junto con el token
            Usuario usuario = usuarioService.buscarPorEmail(loginRequest.getEmail());

            return ResponseEntity.ok(new AuthResponse(jwt, usuario.getNombre(), usuario.getEmail(), usuario.getDni()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UsuarioRegistroDTO registroDTO) {
        try {
            UsuarioResponseDTO usuarioResponseDTO = usuarioService.registrarUsuario(registroDTO);
            String jwt = jwtUtil.generarToken(usuarioResponseDTO.getEmail());

            return ResponseEntity.ok(new AuthResponse(jwt,
                    usuarioResponseDTO.getNombre(),
                    usuarioResponseDTO.getEmail(),
                    usuarioResponseDTO.getDni()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el registro");
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


