package com.theater.model.response;

import lombok.Data;

@Data
public class MovieDTO {
    private Long id;
    private String title;
    private String genre;
    private String language;
}