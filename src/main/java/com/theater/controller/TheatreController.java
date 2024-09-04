package com.theater.controller;

import com.theater.entities.Seat;
import com.theater.entities.Show;
import com.theater.entities.Theatre;
import com.theater.model.request.SeatUpdateRequest;
import com.theater.model.request.ShowRequestDTO;
import com.theater.model.request.TheatreRequest;
import com.theater.model.response.SeatResponseDTO;
import com.theater.model.response.ShowDTO;
import com.theater.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theater")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @GetMapping("/{city}")
    public List<Theatre> getTheatresInCity(@PathVariable String city) {
        return theatreService.getTheatresInCity(city);
    }

    @PostMapping("/onboard")
    public Theatre OnboardTheatre(@RequestBody TheatreRequest theatreRequest) {
        return theatreService.OnboardTheatre(theatreRequest);
    }

    @PostMapping("/{id}/shows")
    public String createShow(@PathVariable Long id, @RequestBody ShowRequestDTO showRequestDTO) {
        Show result  = theatreService.createShow(id, showRequestDTO);
        return "Show created with Id "+ result.getId()  ;
    }

    @GetMapping("/shows")
    public List<ShowDTO> getShows() {
        return theatreService.getShows();
    }

    @GetMapping("/show/{id}")
    public ShowDTO getShow(@PathVariable Long id) {
        return theatreService.getShow(id);
    }

    @PutMapping("/{id}/shows/{showId}")
    public Show updateShow(@PathVariable Long id, @PathVariable Long showId, @RequestBody ShowRequestDTO showRequestDTO) {
        return theatreService.updateShow(showId, showRequestDTO);
    }

    @DeleteMapping("/{id}/shows/{showId}")
    public String deleteShow(@PathVariable Long showId) {
        return theatreService.deleteShow(showId);
    }

}
