package ek.osnb.starter.service;

import ek.osnb.starter.exceptions.NotFoundException;
import ek.osnb.starter.model.Actor;
import ek.osnb.starter.model.Movie;
import ek.osnb.starter.model.MovieDetails;
import ek.osnb.starter.repository.ActorRepository;
import ek.osnb.starter.repository.MovieDetailsRepository;
import ek.osnb.starter.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final MovieDetailsRepository movieDetailsRepository;

    public MovieService(MovieRepository movieRepository, ActorRepository actorRepository, MovieDetailsRepository movieDetailsRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.movieDetailsRepository = movieDetailsRepository;
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (movieOptional.isEmpty()) {
            throw new NotFoundException("Movie not found with id: " + id);
        }
        // Or use shortcut:
        // return movieRepository.findById(id).orElseThrow(() -> new NotFoundException("Movie not found with id: " + id));

        return movieOptional.get();
    }

    public Movie addActorToMovie(Long movieId, Long actorId){
        Optional<Movie> movie = movieRepository.findById(movieId);
        Optional<Actor> actor = actorRepository.findById(actorId);
        if(movie.isEmpty() || actor.isEmpty()){
            if(movie.isEmpty()){
                throw new NotFoundException("Movie not found with id: " + movieId);

            } else {
                throw new NotFoundException("Actor not found with id: " + actorId);
            }
        }
        Movie currMovie = movie.get();
        Actor currActor = actor.get();
        currMovie.setActor(currActor);
        movieRepository.save(currMovie);
        return currMovie;
    }

    public Movie addDetailsToMovie(Long movieId, MovieDetails details){
        Optional<Movie> movie = movieRepository.findById(movieId);
        if(movie.isEmpty()){
            throw new NotFoundException("Movie not found with id: " + movieId);
        }
        Movie updatedMovie = movie.get();
        MovieDetails savedDetails = movieDetailsRepository.save(details);
        updatedMovie.setMovieDetails(savedDetails);
        savedDetails.setMovie(updatedMovie);
        movieRepository.save(updatedMovie);
        return updatedMovie;
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}