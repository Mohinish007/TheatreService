package com.theater.model.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShowRequestDTO {

    private Long theatreId;
    private Long movieId;
    private LocalDateTime showTime;
    private int rowNum;
    private int colNum;
    private BigDecimal price;

}