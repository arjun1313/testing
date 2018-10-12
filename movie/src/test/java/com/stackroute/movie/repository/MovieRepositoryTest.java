package com.stackroute.movie.repository;

import com.stackroute.movie.domain.Movie;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
public class MovieRepositoryTest {

    @Autowired
    MovieRepository movieRepository;
    Movie movie;

    @Before
    public void setUp() {

        movie = new Movie();
        movie.setImdbId(1);
        movie.setMovieTitle("hello");
        movie.setComment("lov story");
        movie.setPosterUrl("https://bookmmyshow.com:hello");
        movie.setRating(4.2);
        movie.setYearOfRelease(2017);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSaveMovie(){
        movieRepository.save(movie);
        Movie fetchMovies = movieRepository.findById(movie.getImdbId()).get();
        Assert.assertEquals(1,fetchMovies.getImdbId());
    }


    @Test
    public void testSaveMovieFailure(){
        movieRepository.save(movie);
        Movie fetchMovies = movieRepository.findById(movie.getImdbId()).get();
        Assert.assertNotEquals(2,fetchMovies.getImdbId());
    }

    @Test
    public void testGetallMovies(){
        Movie movies1 = new Movie(1,"run-fun","https://www.bookmyshow.com",3.8,2016,"love story");
        Movie movies2 = new Movie(2,"get-run","https://www.bookmyshow.com",4.2,2017,"crime story");
        movieRepository.save(movies1);
        movieRepository.save(movies2);
        List<Movie> fetchMoviesList = movieRepository.findAll();
        Assert.assertEquals("crime story",fetchMoviesList.get(1).getComment());
    }

    @Test
    public void testUpdateMoviesbyId(){
        Movie movies1 = new Movie(1,"run-fun","https://www.bookmyshow.com",3.8,2016,"love story");
        Movie movies2 = new Movie(2,"get-run","https://www.bookmyshow.com",4.2,2017,"crime story");
        movieRepository.save(movies1);
        movieRepository.save(movies2);
        Movie temp = movieRepository.findById(1).get();
        temp.setComment(movie.getComment());
        movieRepository.save(temp);
        List<Movie> fetchMoviesList = movieRepository.findAll();
        Assert.assertEquals("lov story",fetchMoviesList.get(0).getComment());
    }

    @Test
    public void testDeleteMovies(){
        Movie movies1 = new Movie(1,"run-fun","https://www.bookmyshow.com",3.8,2016,"love story");
        Movie movies2 = new Movie(2,"get-run","https://www.bookmyshow.com",4.2,2017,"crime story");
        movieRepository.save(movies1);
        movieRepository.save(movies2);
        movieRepository.deleteById(1);
        List<Movie> fetchMoviesList = movieRepository.findAll();
        Assert.assertEquals(2,fetchMoviesList.get(0).getImdbId());

    }
}