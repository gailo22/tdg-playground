package com.gailo22.saga.seat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class SeatController {

    @Autowired
    private SeatSagaService seatSagaService;

    @PostMapping("/book-seat")
    public BookSeatResponse bookSeat(@RequestBody BookSeatRequest bookSeatRequest) {
        String bookingId = UUID.randomUUID().toString();
        Seat seat = seatSagaService.bookSeat(new BookSeatDetails(bookingId, bookSeatRequest.getSeatId(), bookSeatRequest.getCustomerId()));
        return new BookSeatResponse(bookingId, seat.getSeatId());
    }

//    @GetMapping(value="/seats/{bookingId}")
//    public ResponseEntity<GetBookSeatResponse> getOrder(@PathVariable String bookingId) {
//        return bookingRepository
//                .findById(bookingId)
//                .map(o -> new ResponseEntity<>(new GetBookSeatResponse(o.getId(), o.getState(), o.getRejectionReason()), HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
}
