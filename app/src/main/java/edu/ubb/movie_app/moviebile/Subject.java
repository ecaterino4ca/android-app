package edu.ubb.movie_app.moviebile;

import java.util.List;
import java.util.Map;

/**
 * Created by ecaterina on 1/13/18.
 */

public interface Subject {
    void registerObserver(RepositoryObserver repositoryObserver);
    void removeObserver(RepositoryObserver repositoryObserver);
    void notifyObservers();
    void insert(Movie movie);
    void delete(Movie movie);
    void update(Movie movie);
    List<Movie> getMovieList();
    Map<String, Integer> getChartData();
}