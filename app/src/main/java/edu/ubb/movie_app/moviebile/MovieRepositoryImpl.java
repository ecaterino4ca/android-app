package edu.ubb.movie_app.moviebile;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ecaterina on 10/28/17.
 */

public class MovieRepositoryImpl {

    private MovieDatabase dbHelper;

    public MovieRepositoryImpl(Context context) {
        dbHelper = new MovieDatabase(context);
    }

    public int insert(Movie movie) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.MovieEntry.COLUMN_NAME_TITLE, movie.getTitle());
        values.put(DBContract.MovieEntry.COLUMN_NAME_RATING, movie.getRating());
        values.put(DBContract.MovieEntry.COLUMN_NAME_CAST, movie.getCast());
        values.put(DBContract.MovieEntry.COLUMN_NAME_YEAR, movie.getYear());
        values.put(DBContract.MovieEntry.COLUMN_NAME_DIRECTOR, movie.getDirector());
        values.put(DBContract.MovieEntry.COLUMN_NAME_GENRES, movie.getGenres());

        // Inserting Row
        long movie_Id = db.insert(DBContract.MovieEntry.TABLE_NAME, null, values);
        db.close(); // Closing database connection
        Log.i("INFO INSERT", "id: " + movie_Id);
        Log.i("INFO INSERT GET BY ID", getMovieById(movie_Id).toString());
        return (int) movie_Id;
    }

    public void delete(String title) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(DBContract.MovieEntry.TABLE_NAME, DBContract.MovieEntry.COLUMN_NAME_TITLE + "= ?", new String[] { title });
        db.close(); // Closing database connection
    }

    public void update(Movie movie) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBContract.MovieEntry.COLUMN_NAME_RATING, movie.getRating());
        values.put(DBContract.MovieEntry.COLUMN_NAME_CAST, movie.getCast());
        values.put(DBContract.MovieEntry.COLUMN_NAME_YEAR, movie.getYear());
        values.put(DBContract.MovieEntry.COLUMN_NAME_DIRECTOR, movie.getDirector());
        values.put(DBContract.MovieEntry.COLUMN_NAME_GENRES, movie.getGenres());

        Log.i("INFO update", "title: " + movie.getTitle());

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(DBContract.MovieEntry.TABLE_NAME, values, DBContract.MovieEntry.COLUMN_NAME_TITLE + "= ?", new String[] { movie.getTitle() });
        db.close(); // Closing database connection
    }

    public List<Movie> getMovieList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  *" +
                " FROM " + DBContract.MovieEntry.TABLE_NAME;

        List<Movie> movieList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(cursor.getLong(cursor.getColumnIndex(DBContract.MovieEntry._ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_TITLE)));
                movie.setRating(cursor.getInt(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_RATING)));
                movie.setYear(cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_YEAR)));
                movie.setGenres(cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_GENRES)));
                movie.setCast(cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_CAST)));
                movie.setDirector(cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_DIRECTOR)));
                movieList.add(movie);
                Log.i("INFO GET MOVIE LIST:", movie.toString());

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return movieList;

    }

    public Movie getMovieById(long Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT *" +
                " FROM " + DBContract.MovieEntry.TABLE_NAME
                + " WHERE " +
                DBContract.MovieEntry._ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        Movie movie = new Movie();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                movie.setId(cursor.getLong(cursor.getColumnIndex(DBContract.MovieEntry._ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_TITLE)));
                movie.setRating(cursor.getInt(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_RATING)));
                movie.setYear(cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_YEAR)));
                movie.setGenres(cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_GENRES)));
                movie.setCast(cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_CAST)));
                movie.setDirector(cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_DIRECTOR)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return movie;
    }

    public Map<String, Integer> getChartData(){
        Map<String, Integer> result = new HashMap<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT " +
                DBContract.MovieEntry.COLUMN_NAME_GENRES + " , AVG(" +
                DBContract.MovieEntry.COLUMN_NAME_RATING + ") AS Average" +
                " FROM " + DBContract.MovieEntry.TABLE_NAME +
                " GROUP BY " + DBContract.MovieEntry.COLUMN_NAME_GENRES;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                result.put(cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_GENRES)), cursor.getInt(cursor.getColumnIndex("Average")));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return result;
    }

}
