package com.gailo22.saga.payment;

import com.gailo22.saga.commands.MakePaymentCommand;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class PaymentCommandHandler {

    private PaymentService paymentService;

    public PaymentCommandHandler(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public CommandHandlers commandHandlerDefinitions() {
        return SagaCommandHandlersBuilder
                .fromChannel("paymentServiceChannel")
                .onMessage(MakePaymentCommand.class, this::makePayment)
                .build();
    }

    private Message makePayment(CommandMessage<MakePaymentCommand> cm) {
        MakePaymentCommand command = cm.getCommand();
        try {
            String paymentId = paymentService.makePayment(command.getBookingId(), command.getCustomerId(), command.getAmount());
            return withSuccess(new PaymentSuccess(paymentId));
        } catch (Exception ex) {
            return withFailure(new PaymentFail());
        }
    }
}
