package com.stackroute.movie.service;

import com.stackroute.movie.domain.Movie;
import com.stackroute.movie.exceptions.MovieAlreadyExistsException;
import com.stackroute.movie.exceptions.NullDetailsException;
import com.stackroute.movie.repository.MovieRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    Movie movie;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieServiceImpl movieService;
    List<Movie> list ;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        movie = new Movie();
        movie.setImdbId(1);
        movie.setMovieTitle("hello");
        movie.setComment("love story");
        movie.setPosterUrl("https://bookmmyshow.com:hello");
        movie.setRating(4.2);
        movie.setYearOfRelease(2017);
        list = new ArrayList<>();
        /*list.add(movie);*/
    }

    @Test
    public void addMovie() throws MovieAlreadyExistsException {

        when(movieRepository.save(any())).thenReturn(movie);
        Movie savedMovies = movieService.addMovie(movie);
        Assert.assertEquals(movie,savedMovies);
        verify(movieRepository,times(1)).save(movie);
    }

    @Test
    public void addMovieFailure() throws MovieAlreadyExistsException {

        when(movieRepository.save(any())).thenReturn(null);
        Movie savedMovies = movieService.addMovie(movie);
        Assert.assertNotEquals(movie,savedMovies);
        verify(movieRepository,times(1)).save(movie);
    }

    @Test
    public void getAllMovies() throws NullDetailsException {
        when(movieRepository.save(any())).thenReturn(list);
        List<Movie> movielist = movieService.getAllMovies();
        logger.info("yup logger is working");
        Assert.assertEquals(list,movielist);
        verify(movieRepository,times(1)).findAll();
    }

    @Test
    public void getAllMoviesFailure() throws NullDetailsException {

        when(movieRepository.save(any())).thenReturn(list);
        list.add(movie);
        List<Movie> movielist = movieService.getAllMovies();
        System.out.println(movielist);
        Assert.assertNotEquals(list,movielist);
        verify(movieRepository,times(1)).findAll();
    }

    @Test
    public void updateMovie() {
    }

    @Test
    public void deleteMovie() {
    }

    @Test
    public void getByMovieId() {
    }

    @Test
    public void getMovieByName() {
    }
}