package edu.ubb.movie_app.moviebile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ecaterina on 10/28/17.
 */

public class MovieRepositoryImpl {

    private List<Movie> movies = new ArrayList<>();
    private static MovieRepositoryImpl instance = new MovieRepositoryImpl();

    private MovieRepositoryImpl() {
        initialize();
    }

    private void initialize(){
        movies.add(new Movie("The Shawshank redemprion", "1994", "9.2", "Crime, Drama", " Tim Robbins, Morgan Freeman, Bob Gunton", "Frank Darabont"));
        movies.add(new Movie("The Lord of the Rings", "2003", "8.9", "Adventure, Drama, Fantasy", " Elijah Wood, Viggo Mortensen, Ian McKellen", "Peter Jackson" ));
        movies.add(new Movie("Inception", "2010", "8.7", "Action, Adventure, Sci-Fi", "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page", "Christopher Nolan"));
        movies.add(new Movie("The Matrix", "1999", "8.7", "Action, Sci-Fi", "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss", "Lana Wachowski"));
        movies.add(new Movie("Interstellar", "2014", "8.5", "Adventure, Drama, Sci-Fi", "Matthew McConaughey, Anne Hathaway, Jessica Chastain", "Christopher Nolan"));
    }

    public List<Movie> getAllMovies() {
        return movies;
    }

    public Movie findByTitle(String title) {
        for (Movie movie: getAllMovies()) {
            if (movie.getTitle().equals(title)){
                return movie;
            }
        }
        return null;
    }

    public void update(Movie movie) {
        int index = -1;
        for (int i = 0; i < movies.size() ; i++) {
            if (movie.getTitle().equals(movies.get(i).getTitle())){
                index = i;
            }
        }
        if(index != -1){
            movies.set(index, movie);
        }
    }

    public static MovieRepositoryImpl getInstance() {
        return instance;
    }
}
