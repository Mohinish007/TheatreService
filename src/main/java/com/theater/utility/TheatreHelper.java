package com.theater.utility;

import com.theater.entities.Seat;
import com.theater.entities.Show;
import com.theater.model.request.ShowRequestDTO;

import java.util.ArrayList;
import java.util.List;

public class TheatreHelper {

    public static List<Seat> getSeats(ShowRequestDTO showRequestDTO, Show show) {
        List<Seat> seats = new ArrayList<>();
        char rowChar = 'A';
        for (int i = 0; i < showRequestDTO.getRowNum(); i++) {
            for (int j = 1; j <= showRequestDTO.getColNum(); j++) {
                Seat seat = new Seat();
                seat.setMovieShow(show);
                seat.setRowNum(String.valueOf(rowChar));
                seat.setColumnNum(j);
                seat.setSeatNumber(rowChar + String.valueOf(j));
                seat.setStatus(SeatStatus.AVAILABLE);
                seats.add(seat);
            }
            rowChar++;
        }
        return seats;
    }
}
