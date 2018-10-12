package com.stackroute.movie.repository;

import com.stackroute.movie.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByMovieTitle(@Param("movieTitle") String  movieTitle);
}
