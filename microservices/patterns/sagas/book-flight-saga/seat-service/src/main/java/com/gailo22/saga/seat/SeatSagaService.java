package com.gailo22.saga.seat;

import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class SeatSagaService {

    @Autowired
    private SagaInstanceFactory sagaInstanceFactory;

    @Autowired
    private BookSeatSaga bookSeatSaga;

    @Autowired
    private SeatRepository seatRepository;

    public SeatSagaService(SagaInstanceFactory sagaInstanceFactory, BookSeatSaga bookSeatSaga, SeatRepository seatRepository) {
        this.sagaInstanceFactory = sagaInstanceFactory;
        this.bookSeatSaga = bookSeatSaga;
        this.seatRepository = seatRepository;
    }

    @Transactional
    public Seat bookSeat(BookSeatDetails bookSeatDetails) {
        BookSeatSagaData data = new BookSeatSagaData(bookSeatDetails);
        sagaInstanceFactory.create(bookSeatSaga, data);
        return seatRepository.findById(data.getBookSeatDetails().getSeatId()).get();
    }
}
