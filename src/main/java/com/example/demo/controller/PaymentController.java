package com.example.demo.controller;

import com.example.demo.dto.CrearOrdenRequest;
import com.example.demo.dto.CrearPreferenceRequest;
import com.example.demo.services.MercadoPagoService;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final MercadoPagoService mercadoPagoService;

    // Cambio: ahora recibo el ID de orden y productos para crear preferencia
    @PostMapping("/create-preference")
    public ResponseEntity<?> createPreference(@RequestBody CrearPreferenceRequest request) {
        try {
            Preference preference = mercadoPagoService.createPreference(request);

            Map<String, Object> response = new HashMap<>();
            response.put("initPoint", preference.getInitPoint());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear preferencia de pago");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> recibirWebhook(@RequestBody Map<String, Object> payload) {

        return ResponseEntity.ok("ok");
    }
}

