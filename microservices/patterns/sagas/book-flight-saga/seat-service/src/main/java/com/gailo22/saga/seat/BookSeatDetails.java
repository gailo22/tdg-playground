package com.gailo22.saga.seat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookSeatDetails {

    private String bookingId;
    private String seatId;
    private String customerId;

    public BookSeatDetails(){}

}
