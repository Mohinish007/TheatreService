package com.theater.model.request;

import lombok.Data;

import java.util.List;

@Data
public class SeatUpdateRequest {

    private Long showId;
    List<SeatRequest> seatRequestList;

}