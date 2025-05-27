package com.example.demo.services;

import com.example.demo.dto.PaymentRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {

    public Preference createPreference(PaymentRequest request) {
        try {
            // Crear el item del producto
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id("ITEM_" + System.currentTimeMillis())
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .quantity(request.getQuantity())
                    .currencyId("ARS")
                    .unitPrice(request.getPrice())
                    .build();

            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            // Configurar URLs de retorno
            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("https://bf42-200-80-186-219.ngrok-free.app/success")
                    .failure("https://bf42-200-80-186-219.ngrok-free.app/failure")
                    .pending("https://bf42-200-80-186-219.ngrok-free.app/pending")
                    .build();

            // Crear la preferencia
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .notificationUrl("https://4fbe-2803-9800-9846-80eb-ca0-77bf-e279-b957.ngrok-free.app/api/payments/webhook")
                    .autoReturn("approved")
                    .externalReference("ORDER_" + System.currentTimeMillis())
                    .build();

            PreferenceClient client = new PreferenceClient();
            return client.create(preferenceRequest);

        } catch (MPApiException e) {
            throw new RuntimeException("Error API MercadoPago: " + e.getApiResponse().getContent());
        } catch (MPException e) {
            throw new RuntimeException("Error MercadoPago: " + e.getMessage());
        }
    }

}