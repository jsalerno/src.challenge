package au.com.practica.src.challenge.service;

import org.springframework.stereotype.Component;

import au.com.practica.src.challenge.bean.Movie;
import au.com.practica.src.challenge.repo.MovieRepository;

@Component
public class MovieReadService extends ReadService<String, Movie, MovieRepository> {
}
