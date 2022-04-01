package com.gailo22.saga.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentSuccess {
    private String paymentId;
}
