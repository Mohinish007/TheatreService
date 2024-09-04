package com.theater.model.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public @Data @Builder(access = AccessLevel.PUBLIC)
 class ShowDTO {
    private Long id;
    private TheatreDTO theatreDTO;
    private MovieDTO movieDTO;
    private LocalDateTime showTime;
    private List<SeatResponseDTO> seats;
    private BigDecimal price;


}