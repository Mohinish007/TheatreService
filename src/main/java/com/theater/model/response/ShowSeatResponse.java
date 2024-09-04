package com.theater.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShowSeatResponse {
    private Long showId;
    private String rowNum;
    private int columnNum;
    private List<SeatResponseDTO> seatNumber;

}