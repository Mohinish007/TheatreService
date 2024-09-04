package com.theater.controller;

import com.theater.config.SeatLockService;
import com.theater.model.request.SeatUpdateRequest;
import com.theater.model.response.SeatResponseDTO;
import com.theater.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show/{showId}/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @Autowired
    private SeatLockService seatLockService;

    @PutMapping("/lock")
    public ResponseEntity<?> lockSeat(@PathVariable Long showId, @RequestBody List<String> seatNumbers) {
        seatLockService.lockSeats(showId, seatNumbers);
        return ResponseEntity.ok("Seat locked.");
    }

    @PutMapping("/unlock")
    public ResponseEntity<?> unlockSeat(@PathVariable Long showId,@RequestBody List<String> seatNumbers) {
        seatLockService.unlockSeats(showId, seatNumbers);
        return ResponseEntity.ok("Seat unlocked.");
    }

    @PutMapping("/updateSeatInventory")
    public List<SeatResponseDTO> updateSeatInventory(@RequestBody SeatUpdateRequest seatUpdateRequests) throws Exception {
        return seatService.updateSeatInventory(seatUpdateRequests);
    }
}
