package com.academiadodesenvolvedor.ecommerce_api.usecases.order;

import com.academiadodesenvolvedor.ecommerce_api.entities.Order;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessStripePaymentUseCase {
    private final GetOrderByIdUseCase getOrderByIdUseCase;

    @Value("${app.stripe.private-key}")
    private String apiKey;
    @Value("${app.stripe.success-url}")
    private String successUrl;
    @Value("${app.stripe.cancel-url}")
    private String cancelUrl;

    public void execute(Long orderId) {
        Order order = this.getOrderByIdUseCase.execute(orderId);

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
