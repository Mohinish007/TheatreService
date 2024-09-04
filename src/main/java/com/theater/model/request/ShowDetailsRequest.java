package com.theater.model.request;

import lombok.Data;

@Data
public class ShowDetailsRequest {

    private int totalSeats;
    private int availableSeats;

    // Getters and Setters
}
