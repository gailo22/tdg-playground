package com.gailo22.saga.commands;


import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MakePaymentCommand implements Command {

    private String bookingId;
    private String customerId;
    private Double amount;
}
