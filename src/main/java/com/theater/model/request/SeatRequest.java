package com.theater.model.request;

import com.theater.utility.SeatStatus;
import lombok.Data;

@Data
public class SeatRequest {
    private Long id;
    private String seatNumber;
    private SeatStatus status;

}