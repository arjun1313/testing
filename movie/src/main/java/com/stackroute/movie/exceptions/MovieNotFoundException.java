package com.stackroute.movie.exceptions;

public class MovieNotFoundException extends Exception{

    String message;
    public MovieNotFoundException(String message){
         super(message);
         this.message =message;

    }
}
