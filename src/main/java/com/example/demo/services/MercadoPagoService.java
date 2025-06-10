package com.example.demo.services;

import com.example.demo.dto.CrearPreferenceRequest;
import com.example.demo.dto.ProductoPagoDTO;
import com.example.demo.model.Detalle;
import com.example.demo.model.OrdenCompra;
import com.example.demo.model.OrdenCompraDetalle;
import com.example.demo.model.Usuario;
import com.example.demo.repository.DetalleRepository;
import com.example.demo.repository.OrdenCompraRepository;
import com.example.demo.repository.UsuarioRepository;
import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MercadoPagoService {

    private final OrdenCompraRepository ordenCompraRepository;
    private final DetalleRepository detalleRepository;
    private final UsuarioRepository usuarioRepository;
    private final OrdenCompraService ordenCompraService;

    public OrdenCompra crearOrdenCompra(CrearPreferenceRequest request, Long userId) {
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setFecha(LocalDate.now());
        ordenCompra.setEstado("APROBADO");

        if (userId != null) {
            Usuario usuario = usuarioRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));
            ordenCompra.setUsuario(usuario);
        }

        float total = 0.0f;
        List<com.example.demo.model.OrdenCompraDetalle> detallesOrden = new ArrayList<>();
        for (ProductoPagoDTO productoPago : request.getProductos()) {
            Detalle productoDetalle = detalleRepository.findById(productoPago.getDetalleId())
                    .orElseThrow(() -> new RuntimeException("Detalle no encontrado para el ID: " + productoPago.getDetalleId()));

            com.example.demo.model.OrdenCompraDetalle ocDetalle = new com.example.demo.model.OrdenCompraDetalle();
            ocDetalle.setDetalle(productoDetalle);
            ocDetalle.setCantidad(productoPago.getCantidad());
            ocDetalle.setSubtotal(productoDetalle.getPrecio().getPrecioVenta() * productoPago.getCantidad());
            ocDetalle.setOrdenCompra(ordenCompra);

            detallesOrden.add(ocDetalle);
            total += ocDetalle.getSubtotal();
        }
        ordenCompra.setDetalles(detallesOrden);
        ordenCompra.setTotal(total);

        // Si la orden se guarda como APROBADO, actualizamos el stock aquí directamente
        OrdenCompra savedOrden = ordenCompraService.guardarOrdenCompra(ordenCompra);
        if ("APROBADO".equals(savedOrden.getEstado())) {
            this.actualizarStockProductos(savedOrden);
        }
        return savedOrden;
    }

    public Preference createPreference(OrdenCompra ordenCompra, CrearPreferenceRequest request) {
        try {
            List<PreferenceItemRequest> items = new ArrayList<>();

            for (ProductoPagoDTO producto : request.getProductos()) {
                Detalle detalle = detalleRepository.findById(producto.getDetalleId())
                        .orElseThrow(() -> new RuntimeException("Detalle no encontrado para el ID: " + producto.getDetalleId() + " al crear items MP."));

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
                    .success("https://d97d-2803-9800-9846-80eb-a1db-9381-e2e-d63.ngrok-free.app/success")
                    .failure("https://d97d-2803-9800-9846-80eb-a1db-9381-e2e-d63.ngrok-free.app/failure")
                    .pending("https://d97d-2803-9800-9846-80eb-a1db-9381-e2e-d63.ngrok-free.app/pending")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .notificationUrl("https://d97d-2803-9800-9846-80eb-a1db-9381-e2e-d63.ngrok-free.app/api/payments/webhook")
                    .autoReturn("approved")
                    .externalReference("ORDER_" + ordenCompra.getId())
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);
            return preference;

        } catch (MPApiException e) {
            throw new RuntimeException("Error API MercadoPago al crear preferencia: " + e.getApiResponse().getContent(), e);
        } catch (MPException e) {
            throw new RuntimeException("Error MercadoPago: " + e.getMessage(), e);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al crear preferencia: " + e.getMessage(), e);
        }
    }

    public void procesarPago(Long paymentId) {
        try {
            PaymentClient paymentClient = new PaymentClient();
            Payment payment = paymentClient.get(paymentId);

            if (payment == null) {
                return;
            }

            String externalReference = payment.getExternalReference();
            if (externalReference == null || !externalReference.startsWith("ORDER_")) {
                return;
            }

            Long ordenId;
            try {
                ordenId = Long.valueOf(externalReference.replace("ORDER_", ""));
            } catch (NumberFormatException e) {
                return;
            }

            Optional<OrdenCompra> ordenOptional = ordenCompraRepository.findById(ordenId);
            if (!ordenOptional.isPresent()) {
                return;
            }

            OrdenCompra orden = ordenOptional.get();
            String nuevoEstado = this.mapearEstadoPago(payment.getStatus());

            if (!nuevoEstado.equals(orden.getEstado())) { // Solo actualiza si el estado cambió
                orden.setEstado(nuevoEstado);
                orden.setFechaActualizacion(LocalDateTime.now());
                OrdenCompra ordenActualizada = ordenCompraRepository.save(orden);

                if ("APROBADO".equals(nuevoEstado)) {
                    this.actualizarStockProductos(ordenActualizada);
                }
            }

        } catch (MPApiException e) {

        } catch (MPException e) {

        } catch (Exception e) {

        }
    }

    public void procesarMerchantOrder(Long merchantOrderId) {
        try {
            MerchantOrderClient merchantOrderClient = new MerchantOrderClient();
            MerchantOrder merchantOrder = merchantOrderClient.get(merchantOrderId);

            if (merchantOrder == null) {
                return;
            }

            if (merchantOrder.getPayments() != null && !merchantOrder.getPayments().isEmpty()) {
                for (com.mercadopago.resources.merchantorder.MerchantOrderPayment payment : merchantOrder.getPayments()) {
                    this.procesarPago(payment.getId());
                }
            }
        } catch (MPApiException e) {

        } catch (MPException e) {

        } catch (Exception e) {

        }
    }

    private String mapearEstadoPago(String estadoMP) {
        if (estadoMP == null) {
            return "PENDIENTE";
        }

        switch (estadoMP.toLowerCase()) {
            case "approved":
                return "APROBADO";
            case "pending":
                return "PENDIENTE";
            case "authorized":
                return "AUTORIZADO";
            case "in_process":
                return "PROCESANDO";
            case "in_mediation":
                return "EN_MEDIACION";
            case "rejected":
                return "RECHAZADO";
            case "cancelled":
                return "CANCELADO";
            case "refunded":
                return "REEMBOLSADO";
            case "charged_back":
                return "CONTRACARGO";
            default:
                return "PENDIENTE";
        }
    }

    private void actualizarStockProductos(OrdenCompra orden) {
        try {
            for (OrdenCompraDetalle detalle : orden.getDetalles()) {
                Detalle producto = detalle.getDetalle();
                int cantidadVendida = detalle.getCantidad();
                int stockActual = producto.getStock();
                int nuevoStock = stockActual - cantidadVendida;

                if (nuevoStock < 0) {
                    nuevoStock = 0;
                }

                producto.setStock(nuevoStock);
                producto.setFechaActualizacion(LocalDateTime.now());
                detalleRepository.save(producto);
            }
        } catch (Exception e) {

        }
    }
}