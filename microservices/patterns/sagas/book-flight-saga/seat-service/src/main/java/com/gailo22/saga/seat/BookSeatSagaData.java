package com.gailo22.saga.seat;

import lombok.Data;

@Data
public class BookSeatSagaData {
    private BookSeatDetails bookSeatDetails;
    private String errorReason;

    public BookSeatSagaData(){}

    public BookSeatSagaData(BookSeatDetails bookSeatDetails) {
        this.bookSeatDetails = bookSeatDetails;
    }
}
