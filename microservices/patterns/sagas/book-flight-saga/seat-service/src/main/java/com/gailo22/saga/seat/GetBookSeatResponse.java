package com.gailo22.saga.seat;

import lombok.Data;
import org.springframework.util.MultiValueMap;

@Data
public class GetBookSeatResponse {
    private String id;
    private String state;
    private String errorReason;
}
