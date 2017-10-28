package edu.ubb.movie_app.moviebile;

/**
 * Created by ecaterina on 10/28/17.
 */

public class Movie {
    private String title;
    private String year;
    private String rating;
    private String genres;
    private String cast;
    private String director;

    public Movie(String title, String year, String rating, String genres, String cast, String director) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.genres = genres;
        this.cast = cast;
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
