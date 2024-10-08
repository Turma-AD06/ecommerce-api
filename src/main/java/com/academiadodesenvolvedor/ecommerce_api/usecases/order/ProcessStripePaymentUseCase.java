package com.academiadodesenvolvedor.ecommerce_api.usecases.order;

import com.academiadodesenvolvedor.ecommerce_api.dto.output.PaymentDto;
import com.academiadodesenvolvedor.ecommerce_api.entities.Order;
import com.academiadodesenvolvedor.ecommerce_api.entities.enums.OrderStatus;
import com.academiadodesenvolvedor.ecommerce_api.exceptions.http.HttpException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessStripePaymentUseCase {
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;

    @Value("${app.stripe.private-key}")
    private String apiKey;
    @Value("${app.stripe.success-url}")
    private String successUrl;
    @Value("${app.stripe.cancel-url}")
    private String cancelUrl;

    public PaymentDto execute(Long orderId) {
        try {
            Order order = this.getOrderByIdUseCase.execute(orderId);
            Stripe.apiKey = apiKey;
            Session session = this.createStripeSession(order);
            
            order.setPaymentIntentId(session.getPaymentIntent());
            order.setPaymentUrl(session.getUrl());
            order.setStatus(OrderStatus.WAITING_PAYMENT);
            this.updateOrderUseCase.execute(order.getId(), order);

            return new PaymentDto(order.getPaymentUrl());
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpException("Não foi possível processar o pagamento", HttpStatus.UNPROCESSABLE_ENTITY);
        }


    }

    private Session createStripeSession(Order order) throws StripeException {
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        order.getOrderItems().forEach(orderItem -> {
            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("BRL")
                                    .setUnitAmount(orderItem.getUnitPrice())
                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                            .setName(orderItem.getProduct().getName())
                                            .build())
                                    .build()
                    )
                    .setQuantity(Long.valueOf(orderItem.getQuantity()))
                    .build();
            lineItems.add(lineItem);
        });

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addAllLineItem(lineItems)
                .build();

        return Session.create(params);
    }
}
