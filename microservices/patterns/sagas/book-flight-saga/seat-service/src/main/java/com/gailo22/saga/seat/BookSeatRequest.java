package com.gailo22.saga.seat;

import lombok.Data;

@Data
public class BookSeatRequest {
    private String seatId;
    private String customerId;
}
