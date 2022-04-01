package com.gailo22.saga.payment;

import lombok.Data;

@Data
public class PaymentFail {
    private String reason;
}
