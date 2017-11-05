package edu.ubb.movie_app.moviebile;

import java.util.List;

/**
 * Created by ecaterina on 10/28/17.
 */

public interface MovieRepository {

    List<Movie> getAllMovies();

    Movie findByTitle(String title);

    void update(Movie movie);

    MovieRepository getInstance();
}
