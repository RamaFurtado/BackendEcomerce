package com.example.demo.services;

import com.example.demo.dto.CrearOrdenRequest;
import com.example.demo.dto.CrearPreferenceRequest;
import com.example.demo.dto.ProductoPagoDTO;
import com.example.demo.model.Detalle;
import com.example.demo.repository.DetalleRepository;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MercadoPagoService {

    private final DetalleRepository detalleRepository;

    public Preference createPreference(CrearPreferenceRequest request) {
        try {
            List<PreferenceItemRequest> items = new ArrayList<>();

            for (ProductoPagoDTO producto : request.getProductos()) {
                Detalle detalle = detalleRepository.findById(producto.getDetalleId())
                        .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));

                String descripcion = detalle.getProducto().getNombre()
                        + " - Color: " + detalle.getColor()
                        + " - Talle: " + detalle.getTalle().getTalle();

                PreferenceItemRequest item = PreferenceItemRequest.builder()
                        .title(detalle.getProducto().getNombre())
                        .description(descripcion)
                        .quantity(producto.getCantidad())
                        .currencyId("ARS")
                        .unitPrice(BigDecimal.valueOf(detalle.getPrecio().getPrecioVenta()))
                        .build();

                items.add(item);
            }

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("https://bf42-200-80-186-219.ngrok-free.app/success")
                    .failure("https://bf42-200-80-186-219.ngrok-free.app/failure")
                    .pending("https://bf42-200-80-186-219.ngrok-free.app/pending")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .notificationUrl("https://4fbe-2803-9800-9846-80eb-ca0-77bf-e279-b957.ngrok-free.app/api/payments/webhook")
                    .autoReturn("approved")
                    .externalReference("ORDER_" + request.getOrdenId()) // Uso ordenId aquí
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
