package com.stackroute.movie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.movie.domain.Movie;
import com.stackroute.movie.exceptions.MovieAlreadyExistsException;
import com.stackroute.movie.exceptions.MovieNotFoundException;
import com.stackroute.movie.exceptions.NullDetailsException;
import com.stackroute.movie.repository.MovieRepository;
import com.stackroute.movie.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private Movie movie;

    @MockBean
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private List<Movie> list=null;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
        movie = new Movie();
        movie.setImdbId(1);
        movie.setMovieTitle("hello");
        movie.setComment("love story");
        movie.setPosterUrl("https://bookmmyshow.com:hello");
        movie.setRating(4.2);
        movie.setYearOfRelease(2017);
        List<Movie> list = new ArrayList<>();
        list.add(movie);
    }

    @Test
    public void addMovie() throws Exception {
        when(movieService.addMovie(any())).thenReturn(movie);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movie/save")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void addMovieFailure() throws Exception {
        when(movieService.addMovie(any())).thenThrow(MovieAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/movie/save")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void getAllMovies() throws Exception {
        when(movieService.getAllMovies()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void getAllMoviesFailure() throws Exception {
        when(movieService.getAllMovies()).thenThrow(NullDetailsException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void deleteMovie() throws Exception{
        when(movieService.deleteMovie(anyInt())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/movie/1")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteMovieFailure() throws Exception{
        when(movieService.deleteMovie(anyInt())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/movie/2")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateMovie() throws Exception{
        when(movieService.updateMovie(any(),eq(movie.getImdbId()))).thenReturn(movie);
        movie.setComment("new movie");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/movie/1")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateMovieFailure() throws Exception{
        when(movieService.updateMovie(any(),eq(movie.getImdbId()))).thenThrow(MovieNotFoundException.class);
        movie.setComment("new movie");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/movie/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getMovieById() throws Exception{
        Optional<Movie> newmovie = Optional.ofNullable(movie);
        when(movieService.getByMovieId(anyInt())).thenReturn(newmovie);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/1")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getMoviebyId() throws Exception{
        when(movieService.getByMovieId(anyInt())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movie/2")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(movie)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    private static String asJsonString(final Object obj)
    {
        try{
            return new ObjectMapper().writeValueAsString(obj);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}