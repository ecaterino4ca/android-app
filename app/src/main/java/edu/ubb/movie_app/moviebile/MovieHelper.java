package edu.ubb.movie_app.moviebile;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by ecaterina on 12/2/17.
 */

public class MovieHelper {
    private static final String TAG = MovieHelper.class.getSimpleName();


    public static Movie fromCursor(Cursor cursor) {
        Movie p = null;
        if (cursor != null) {
            p = new Movie();
            p.setId(cursor.getLong(cursor.getColumnIndex(DBContract.MovieEntry._ID)));
            p.setTitle(cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_TITLE)));
            p.setYear(cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_YEAR)));
            p.setGenres(cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_GENRES)));
            p.setRating(cursor.getInt(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_RATING)));
            p.setCast(cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_CAST)));
            p.setDirector(cursor.getString(cursor.getColumnIndex(DBContract.MovieEntry.COLUMN_NAME_DIRECTOR)));
        }
        return p;
    }

    public static ContentValues fromMovie(Movie p) {
        ContentValues cv = new ContentValues();
        cv.put(DBContract.MovieEntry.COLUMN_NAME_TITLE, p.getTitle());
        cv.put(DBContract.MovieEntry.COLUMN_NAME_GENRES, p.getGenres());
        cv.put(DBContract.MovieEntry.COLUMN_NAME_YEAR, p.getYear());
        cv.put(DBContract.MovieEntry.COLUMN_NAME_RATING, p.getRating());
        cv.put(DBContract.MovieEntry.COLUMN_NAME_DIRECTOR, p.getDirector());
        cv.put(DBContract.MovieEntry.COLUMN_NAME_CAST, p.getCast());
        return cv;
    }
}

