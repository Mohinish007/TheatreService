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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheatreService {

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private SeatService seatService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Theatre> getTheatresInCity(String city) {
        return theatreRepository.findByCity(city);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Show createShow(Long theatreId, ShowRequestDTO showRequestDTO) {
        Theatre theatre = theatreRepository.findById(theatreId)
            .orElseThrow(() -> new ResourceNotFoundException("Theatre not found"));
        Movie movie = movieService.getMovieById(showRequestDTO.getMovieId());
        Show show = new Show();
        show.setTheatre(theatre);
        show.setMovie(movie);
        show.setShowTime(showRequestDTO.getShowTime());
        show.setPrice(showRequestDTO.getPrice());
        show = showRepository.save(show);

        List<Seat> seats = TheatreHelper.getSeats(showRequestDTO, show);


        // show.setSeats(seats);
        seatRepository.saveAll(seats);
        return show;
    }



    public Show updateShow(Long showId, ShowRequestDTO showRequestDTO) {
        Show show = showRepository.findById(showId)
            .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        show.setShowTime(showRequestDTO.getShowTime());
        //show.setSeats(updatedShow.getSeats());
        return showRepository.save(show);
    }

    public String deleteShow(Long showId) {
        showRepository.deleteById(showId);
        return showId + "deleted successfully";
    }


    public Theatre OnboardTheatre(TheatreRequest theatreRequest) {
        Theatre theatre = objectMapper.convertValue(theatreRequest, Theatre.class);
        return theatreRepository.save(theatre);
    }


    public List<ShowDTO> getShows() {
        objectMapper.registerModule(new JavaTimeModule());
        List<Show> showList = showRepository.findAll();
        List<ShowDTO> list = showList.stream().map(s -> {
            return  ShowDTO.builder().id(s.getId()).showTime(s.getShowTime()).price(s.getPrice())
                    .theatreDTO(objectMapper.convertValue(s.getTheatre(), TheatreDTO.class))
                    .movieDTO(objectMapper.convertValue(s.getMovie(), MovieDTO.class))
                    .seats(seatService.getSeats(s.getId(), s.getSeats())).build();
        }).collect(Collectors.toList());
        return list;
    }

    public ShowDTO getShow(Long id) {

        Show show = showRepository.getShowById(id);
        ShowDTO showDTO =  ShowDTO.builder().id(show.getId()).showTime(show.getShowTime()).price(show.getPrice())
                    .theatreDTO(objectMapper.convertValue(show.getTheatre(), TheatreDTO.class))
                    .movieDTO(objectMapper.convertValue(show.getMovie(), MovieDTO.class))
                    .seats(seatService.getSeats(show.getId(), show.getSeats())).build();
        return showDTO;
    }
}
