package edu.ubb.movie_app.moviebile;

import android.provider.BaseColumns;

/**
 * Created by ecaterina on 12/2/17.
 */

public class DBContract {
    //do not instantiate
    private DBContract() {
    }

    /* Inner class that defines the table contents */
    public static class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_NAME_TITLE= "title";
        public static final String COLUMN_NAME_YEAR= "year";
        public static final String COLUMN_NAME_RATING= "rating";
        public static final String COLUMN_NAME_GENRES= "genres";
        public static final String COLUMN_NAME_CAST= "cast";
        public static final String COLUMN_NAME_DIRECTOR = "director";
    }

}
