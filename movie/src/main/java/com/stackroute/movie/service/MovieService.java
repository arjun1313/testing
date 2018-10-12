package com.stackroute.movie.service;

import com.stackroute.movie.domain.Movie;
import com.stackroute.movie.exceptions.MovieAlreadyExistsException;
import com.stackroute.movie.exceptions.MovieNotFoundException;
import com.stackroute.movie.exceptions.NullDetailsException;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    Movie addMovie(Movie movie) throws MovieAlreadyExistsException;
    List<Movie> getAllMovies() throws NullDetailsException;
    Movie updateMovie(String comment , int id) throws MovieNotFoundException;
    boolean deleteMovie(int id) throws MovieNotFoundException;
    Optional<Movie> getByMovieId(int id) throws MovieNotFoundException;
    List<Movie> getMovieByName(String movietitle) throws MovieNotFoundException;
}