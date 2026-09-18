package ek.osnb.starter.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer releaseYear;
    private String genre;
    @Embedded
    private Rating rating;


    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors = new ArrayList<>();



    @OneToOne()
    @JoinColumn(name = "movie_details_id")
    @JsonBackReference
    private MovieDetails movieDetails;

    public Movie() {}

    public Movie(String title, Integer releaseYear, String genre) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Rating getRating(){
        return rating;
    }

    public void setRating(Rating rating){
        this.rating = rating;
    }


    public List<Actor> getActors() {
        return actors;
    }

    public void setActor(Actor actor) {
        actors.add(actor);
    }

    public MovieDetails getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
    }

}
