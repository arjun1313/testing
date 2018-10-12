package com.stackroute.movie.service;

import com.stackroute.movie.domain.Movie;
import com.stackroute.movie.exceptions.MovieAlreadyExistsException;
import com.stackroute.movie.exceptions.MovieNotFoundException;
import com.stackroute.movie.exceptions.NullDetailsException;
import com.stackroute.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie addMovie(Movie movie) throws MovieAlreadyExistsException {

        if (movieRepository.existsById(movie.getImdbId())) {

            throw new MovieAlreadyExistsException("MovieAlreadyExistsException");
        }

        Movie savedMovie = movieRepository.save(movie);
        return savedMovie;
    }

    @Override
    public List<Movie> getAllMovies() throws NullDetailsException {
        if (movieRepository ==null)
        {    throw  new NullDetailsException("no details to show");

        }
        return (List<Movie>) movieRepository.findAll();
    }

    @Override
    public Movie updateMovie(String comment , int id) throws MovieNotFoundException {

        Movie newMovie;
        if (movieRepository.existsById(id)) {
            newMovie = movieRepository.findById(id).get();
            newMovie.setComment(comment);
            movieRepository.save(newMovie);
            return newMovie;
        } else {
            throw new MovieNotFoundException("Movie Not Found With"+id);
        }

    }


    @Override
    public boolean deleteMovie(int id) throws MovieNotFoundException {


        if (movieRepository.existsById(id)) {
            movieRepository.delete(movieRepository.getOne(id));
            return true;
        } else {
            throw new MovieNotFoundException("Movie Not Found With " + id);
        }
    }

    @Override
    public Optional<Movie> getByMovieId(int id) throws MovieNotFoundException {
        if (movieRepository.existsById(id)) {
            return movieRepository.findById(id);
        }
        else    {
            throw new  MovieNotFoundException("Movie Not Found With " + id);
        }
    }

    @Override
    public List<Movie> getMovieByName(String string) throws MovieNotFoundException {
        List<Movie> resultMovies;
        resultMovies =new ArrayList<>();
        resultMovies =movieRepository.findByMovieTitle(string);

        if(resultMovies !=null) {
            return resultMovies;
        }
        else    {
            throw new MovieNotFoundException("Movie Not Found With Name "+string);
        }
    }


}