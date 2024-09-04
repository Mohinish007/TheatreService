package com.theater.model.response;

import lombok.Data;

@Data
public class TheatreDTO {
    private Long id;
    private String name;
    private String city;
    private String address;
}