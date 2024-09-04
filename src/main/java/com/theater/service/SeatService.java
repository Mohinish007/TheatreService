package com.theater.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.theater.config.SeatLockService;
import com.theater.entities.Movie;
import com.theater.entities.Seat;
import com.theater.entities.Show;
import com.theater.entities.Theatre;
import com.theater.exception.ResourceNotFoundException;
import com.theater.model.request.SeatRequest;
import com.theater.model.request.SeatUpdateRequest;
import com.theater.model.request.ShowRequestDTO;
import com.theater.model.request.TheatreRequest;
import com.theater.model.response.MovieDTO;
import com.theater.model.response.SeatResponseDTO;
import com.theater.model.response.ShowDTO;
import com.theater.model.response.TheatreDTO;
import com.theater.repository.SeatRepository;
import com.theater.repository.ShowRepository;
import com.theater.repository.TheatreRepository;
import com.theater.utility.SeatStatus;
import com.theater.utility.TheatreHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatLockService seatLockService;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<SeatResponseDTO> updateSeatInventory(SeatUpdateRequest updatedSeat) throws Exception {
        List<Seat> seats = seatRepository.findByMovieShow_IdAndIdIn(updatedSeat.getShowId(),updatedSeat.getSeatRequestList().stream().map(i -> i.getId()).collect(Collectors.toList()));
        if (seats.isEmpty()) {
            throw new ResourceNotFoundException("Seats not found");
        }
        for (SeatRequest request : updatedSeat.getSeatRequestList()) {
            for (Seat seat : seats) {
                if (seat.getSeatNumber().equals(request.getSeatNumber())) {
                    if(SeatStatus.BOOKED.equals(seat.getStatus())) {
                        throw new Exception("Seat "+ seat.getSeatNumber() +" is already booked");
                    }
                    seat.setStatus(request.getStatus());
                }
            }
        }
        seatRepository.saveAll(seats);
        List<SeatResponseDTO> seatResponseDTO = getSeats(updatedSeat.getShowId(), seats);
        return seatResponseDTO;
    }

    public List<SeatResponseDTO> getSeats(Long showId, List<Seat> seats) {
        List<SeatResponseDTO> seatResponseDTOS = new ArrayList<>();
        for(Seat seat : seats) {
            if (seatLockService.isSeatLocked(showId, seat.getSeatNumber())) {
                seat.setStatus(SeatStatus.RESERVED);
            }
            seatResponseDTOS.add(SeatResponseDTO.builder().seatNumber(seat.getSeatNumber()).id(seat.getId())
                    .status(seat.getStatus()).build());
        }

        return seatResponseDTOS;
    }
}
