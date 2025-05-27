package com.example.demo.controller;

import com.example.demo.dto.PaymentRequest;
import com.example.demo.dto.ProductoPagoDTO;
import com.example.demo.services.MercadoPagoService;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;


@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*") // Para tu frontend
public class PaymentController {

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @PostMapping("/create-preference")
    public ResponseEntity<?> crearPreferencia(@RequestBody List<ProductoPagoDTO> productos) {
        return mercadoPagoService.generarPreferencia(productos);
    }


    // Endpoint para manejar webhooks (opcional)
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestParam Map<String, String> queryParams) {
        try {
            String type = queryParams.get("type");
            String action = queryParams.get("action");
            String dataId = queryParams.get("data.id");

            if ("payment".equals(type) && "payment.created".equals(action)) {
                // Obtener detalles del pago
                PaymentClient paymentClient = new PaymentClient();
                Payment payment = paymentClient.get(Long.valueOf(dataId));

                System.out.println("📦 Webhook recibido:");
                System.out.println("Estado del pago: " + payment.getStatus());
                System.out.println("Orden externa: " + payment.getExternalReference());

                // Aquí podés actualizar tu orden en la DB según `externalReference`
            }

            return ResponseEntity.ok("OK");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    @GetMapping("/success")
    public ResponseEntity<String>paymentSucces(
            @RequestParam(required = false) String payment_id,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String external_reference
    ){
        return ResponseEntity.ok("Pago recibido con ID: " + payment_id +
                ", estado: " + status +
                ", referencia: " + external_reference);
    }



    @GetMapping("/failure")
    public ResponseEntity<String> paymentFailure() {
        return ResponseEntity.ok("El pago fue cancelado o falló");
    }

    @GetMapping("/pending")
    public ResponseEntity<String> paymentPending() {
        return ResponseEntity.ok("El pago está pendiente");
    }
}