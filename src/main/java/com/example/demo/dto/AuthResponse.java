package com.example.demo.dto;


import com.example.demo.enums.Rol;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String jwt;
    private String nombre;
    private String email;
    private String rol;
    private String dni;
}
