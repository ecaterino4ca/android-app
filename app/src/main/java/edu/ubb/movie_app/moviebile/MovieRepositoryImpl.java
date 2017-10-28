package edu.ubb.movie_app.moviebile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ecaterina on 10/28/17.
 */

public class MovieRepositoryImpl implements MovieRepository {
    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Shawshank redemprion", "1994", "9.2", "Crime, Drama", " Tim Robbins, Morgan Freeman, Bob Gunton", "Frank Darabont"));
        movies.add(new Movie("The Lord of the Rings", "2003", "8.9", "Adventure, Drama, Fantasy", " Elijah Wood, Viggo Mortensen, Ian McKellen", "Peter Jackson" ));
        movies.add(new Movie("Inception", "2010", "8.7", "Action, Adventure, Sci-Fi", "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page", "Christopher Nolan"));
        movies.add(new Movie("The Matrix", "1999", "8.7", "Action, Sci-Fi", "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss", "Lana Wachowski"));
        movies.add(new Movie("Interstellar", "2014", "8.5", "Adventure, Drama, Sci-Fi", "Matthew McConaughey, Anne Hathaway, Jessica Chastain", "Christopher Nolan"));
        return movies;
    }
}
