package com.gailo22.saga.seat;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "SEAT")
public class Seat {
    @Id
    private String seatId;
    private String status;
}
