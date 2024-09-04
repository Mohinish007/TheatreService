package com.theater.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theater.entities.Movie;
import com.theater.model.request.MovieDto;
import com.theater.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
    }

    public Movie addMovie(MovieDto movieDto) {
        return movieRepository.save(objectMapper.convertValue(movieDto, Movie.class));
    }

    public Movie updateMovie(Long movieId, MovieDto movieDetails) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
        
        movie.setTitle(movieDetails.getTitle());
        movie.setGenre(movieDetails.getGenre());
        movie.setLanguage(movieDetails.getLanguage());
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long movieId) {
        movieRepository.deleteById(movieId);
    }
}
