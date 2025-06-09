package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class CrearPreferenceRequest {
    private Long ordenId;
    private List<ProductoPagoDTO> productos;
}
