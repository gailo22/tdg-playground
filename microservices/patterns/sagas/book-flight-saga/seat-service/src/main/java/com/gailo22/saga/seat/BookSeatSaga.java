package com.gailo22.saga.seat;

import com.gailo22.saga.commands.MakePaymentCommand;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;

public class BookSeatSaga implements SimpleSaga<BookSeatSagaData> {

    private SeatService seatService;

    public BookSeatSaga(SeatService seatService) {
        this.seatService = seatService;
    }

    private SagaDefinition<BookSeatSagaData> sagaDefinition =
            step()
                    .invokeLocal(this::blockSeat)
                    .withCompensation(this::unblockSeat)
                    .step()
                    .invokeParticipant(this::makePayment)
                    .onReply(PaymentFail.class, this::handlePaymentFail)
                    .step()
                    .invokeLocal(this::allocateSeat)
                    .withCompensation(this::moveSeatBackToPool)
                    .build();

    private void blockSeat(BookSeatSagaData data) {
        seatService.blockSeat(data.getBookSeatDetails());
    }

    private void unblockSeat(BookSeatSagaData data) {
        seatService.unblockSeat(data.getBookSeatDetails());
    }

    private CommandWithDestination makePayment(BookSeatSagaData data) {
        String bookingId = data.getBookSeatDetails().getBookingId();
        String customerId = data.getBookSeatDetails().getCustomerId();
        Double amount = 99.9;
        return CommandWithDestinationBuilder.send(new MakePaymentCommand(bookingId, customerId, amount))
                .to("paymentServiceChannel")
                .build();
    }

    private void handlePaymentFail(BookSeatSagaData data, PaymentFail paymentFail) {
        data.setErrorReason(paymentFail.getReason());
    }

    private void allocateSeat(BookSeatSagaData data) {
        seatService.allocateSeat(data.getBookSeatDetails());
    }

    private void moveSeatBackToPool(BookSeatSagaData data) {
        seatService.deAllocateSeat(data.getBookSeatDetails());
    }

    @Override
    public SagaDefinition<BookSeatSagaData> getSagaDefinition() {
        return sagaDefinition;
    }
}
