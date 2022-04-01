package com.gailo22.saga.seat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookSeatResponse {
    private String bookingId;
    private String seatId;
}
