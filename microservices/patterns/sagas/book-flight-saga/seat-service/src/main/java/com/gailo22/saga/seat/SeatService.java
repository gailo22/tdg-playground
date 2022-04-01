package com.gailo22.saga.seat;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat blockSeat(BookSeatDetails bookSeatDetails) {
        Optional<Seat> seatOptional = updateSeatStatus(bookSeatDetails, "block");
        return seatOptional.orElseThrow(SeatNotFoundException::new);
    }

    public void unblockSeat(BookSeatDetails bookSeatDetails) {
        updateSeatStatus(bookSeatDetails, "available");
    }

    public Seat allocateSeat(BookSeatDetails bookSeatDetails) {
        Optional<Seat> seatOptional = updateSeatStatus(bookSeatDetails, "booked");
        return seatOptional.orElseThrow(SeatNotFoundException::new);
    }

    public void deAllocateSeat(BookSeatDetails bookSeatDetails) {
        updateSeatStatus(bookSeatDetails, "available");
    }

    private Optional<Seat> updateSeatStatus(BookSeatDetails bookSeatDetails, String status) {
        Optional<Seat> seatFromDb = seatRepository.findById(bookSeatDetails.getSeatId());
        if (seatFromDb.isPresent()) {
            Seat seat1 = seatFromDb.get();
            seat1.setStatus(status);
            seatRepository.save(seat1);
            return seatFromDb;
        }
        return Optional.empty();
    }
}
