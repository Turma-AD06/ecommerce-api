package com.academiadodesenvolvedor.ecommerce_api.controllers;

import com.academiadodesenvolvedor.ecommerce_api.entities.Order;
import com.academiadodesenvolvedor.ecommerce_api.entities.enums.OrderStatus;
import com.academiadodesenvolvedor.ecommerce_api.repositories.OrderRepository;
import com.academiadodesenvolvedor.ecommerce_api.usecases.order.UpdateOrderUseCase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebhookController {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final OrderRepository orderRepository;
    private final UpdateOrderUseCase updateOrderUseCase;
    @Value("${app.stripe.endpoint-secret}")
    private String endpointSecret;

    @PostMapping("/stripe")
    public String handleStripeEvents(@RequestBody String payload,
                                     @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            if ("payment_intent.succeeded".equals(event.getType())) {
                String rawJson = event.getDataObjectDeserializer().getRawJson();
                Map<String, Object> paymentIntent = objectMapper.readValue(rawJson, new TypeReference<Map<String,
                        Object>>() {
                });
                String piId = (String) paymentIntent.get("id");
                Optional<Order> orderOptional = this.orderRepository.findByPaymentIntentId(piId);
                if (orderOptional.isPresent()) {
                    Order order = orderOptional.get();
                    order.setStatus(OrderStatus.PAID);
                    this.updateOrderUseCase.execute(order.getId(), order);
                }
            }
            return "Processado!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error " + e.getMessage();
        }
    }
}
