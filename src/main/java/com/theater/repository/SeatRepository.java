package com.theater.repository;

import com.theater.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    //List<Seat> findByMovieShowId(Long showId);

    List<Seat> findByMovieShow_IdAndIdIn(Long showId, List<Long> seatIds);
}
