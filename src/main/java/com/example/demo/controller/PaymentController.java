package com.example.demo.controller;

import com.example.demo.dto.CrearPreferenceRequest;
import com.example.demo.model.OrdenCompra;
import com.example.demo.services.MercadoPagoService;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final MercadoPagoService mercadoPagoService;

    @PostMapping("/create-checkout")
    public ResponseEntity<?> createCheckout(@RequestBody CrearPreferenceRequest request, @RequestParam Long userId) {
        try {
            OrdenCompra nuevaOrden = mercadoPagoService.crearOrdenCompra(request, userId);

            Preference preference = mercadoPagoService.createPreference(nuevaOrden, request);

            Map<String, Object> response = new HashMap<>();
            response.put("initPoint", preference.getInitPoint());
            response.put("ordenId", nuevaOrden.getId());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear la orden y preferencia de pago");
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> recibirWebhook(@RequestBody Map<String, Object> payload) {
        try {
            String type = (String) payload.get("type");
            String topic = (String) payload.get("topic");
            String resource = (String) payload.get("resource");

            if ("payment".equals(type)) {
                Map<String, Object> data = (Map<String, Object>) payload.get("data");
                if (data != null && data.containsKey("id")) {
                    Long paymentId = Long.valueOf(data.get("id").toString());
                    mercadoPagoService.procesarPago(paymentId);
                } else {
                    return ResponseEntity.badRequest().body("Payload de pago inválido");
                }
            }
            else if ("merchant_order".equals(topic)) {
                if (resource != null && resource.contains("merchant_orders/")) {
                    try {
                        String merchantOrderIdStr = resource.substring(resource.lastIndexOf("/") + 1);
                        Long merchantOrderId = Long.valueOf(merchantOrderIdStr);
                        mercadoPagoService.procesarMerchantOrder(merchantOrderId);
                    } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Error al procesar merchant order ID");
                    }
                } else {
                    return ResponseEntity.badRequest().body("Payload de merchant order inválido");
                }
            }
            else if ("payment".equals(topic)) {
                if (resource != null && resource.contains("payments/")) {
                    try {
                        String paymentIdStr = resource.substring(resource.lastIndexOf("/") + 1);
                        Long paymentId = Long.valueOf(paymentIdStr);
                        mercadoPagoService.procesarPago(paymentId);
                    } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Error al procesar payment ID");
                    }
                } else {
                    return ResponseEntity.badRequest().body("Payload de payment inválido");
                }
            }
            else {
                return ResponseEntity.ok("Tipo de webhook no manejado");
            }

            return ResponseEntity.ok("Webhook procesado");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error procesando webhook");
        }
    }
}

