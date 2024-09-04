package com.theater.model.request;

import lombok.Data;

@Data
public class TheatreRequest {

    private String name;
    private String city;
    private String address;
   // private List<ShowDTO> shows;

    // Getters and Setters
    /*public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ShowDTO> getShows() {
        return shows;
    }

    public void setShows(List<ShowDTO> shows) {
        this.shows = shows;
    }*/
}