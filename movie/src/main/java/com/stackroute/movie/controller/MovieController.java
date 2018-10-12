package com.stackroute.movie.controller;


import com.stackroute.movie.domain.Movie;
import com.stackroute.movie.exceptions.MovieAlreadyExistsException;
import com.stackroute.movie.exceptions.MovieNotFoundException;
import com.stackroute.movie.exceptions.NullDetailsException;
import com.stackroute.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/movie")
public class MovieController {

    MovieService movieService;

    @Autowired
    public MovieController( MovieService movieService) {
        this.movieService =movieService;
    }
    public boolean isParsable(String id){
        try {
            int i=Integer.parseInt(id);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMovie(@RequestBody Movie movie){
        ResponseEntity responseEntity;
        try
        {
            Movie savedMovie= movieService.addMovie(movie);
            responseEntity = new ResponseEntity<Movie>(savedMovie, HttpStatus.CREATED);
        }
        catch (MovieAlreadyExistsException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);

        }
        return responseEntity;
    }

    @GetMapping()
    public ResponseEntity<?> getallMovies(){
        ResponseEntity responseEntity ;
        try{
            responseEntity = new ResponseEntity<List<Movie>>(movieService.getAllMovies(),HttpStatus.OK);
        }
        catch (NullDetailsException ex){
            responseEntity =new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @PutMapping("{id}")
    public ResponseEntity<?>updateMovie(@RequestBody String string,@PathVariable("id") int id){
        ResponseEntity responseEntity;
        try{

            responseEntity = new ResponseEntity<Movie>( movieService.updateMovie(string,id),HttpStatus.OK);
        }
        catch (MovieNotFoundException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @DeleteMapping("{id}")
    public  ResponseEntity<?> deleteMovie(@PathVariable("id") int id){
        ResponseEntity responseEntity;
        try{
            movieService.deleteMovie(id);
            responseEntity= new ResponseEntity("deleted successfully",HttpStatus.OK);
        }
        catch (MovieNotFoundException ex){
            responseEntity =new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getbyMovieId(@PathVariable("id") String id){
        ResponseEntity responseEntity ;
        try{
            if(isParsable(id)) {
                int i = Integer.parseInt(id);
                responseEntity = new ResponseEntity <Optional<Movie>>(movieService.getByMovieId(i), HttpStatus.OK);
            }
            else {
                responseEntity = new ResponseEntity<List<Movie>>(movieService.getMovieByName(id), HttpStatus.OK);
            }
        }
        catch (Exception ex){
            responseEntity =new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

}
