package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "El email no puede estar vacio")
    @Email(message = "Ingrese un email valido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacia")
    private String password;
}
