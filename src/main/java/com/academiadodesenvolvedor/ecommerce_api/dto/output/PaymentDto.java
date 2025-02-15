package com.academiadodesenvolvedor.ecommerce_api.dto.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    @JsonProperty("payment_url")
    private String paymentUrl;
}
