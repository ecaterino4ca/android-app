package edu.ubb.movie_app.moviebile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ecaterina on 12/2/17.
 */

public class MovieDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movie.db";
    private static final String TAG = MovieDatabase.class.getSimpleName();
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.MovieEntry.TABLE_NAME + " (" +
                    DBContract.MovieEntry._ID + " LONG PRIMARY KEY AUTOINCREMENT," +
                    DBContract.MovieEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    DBContract.MovieEntry.COLUMN_NAME_YEAR + TEXT_TYPE + COMMA_SEP +
                    DBContract.MovieEntry.COLUMN_NAME_RATING + TEXT_TYPE + COMMA_SEP +
                    DBContract.MovieEntry.COLUMN_NAME_GENRES + TEXT_TYPE + COMMA_SEP +
                    DBContract.MovieEntry.COLUMN_NAME_CAST + TEXT_TYPE + COMMA_SEP +
                    DBContract.MovieEntry.COLUMN_NAME_DIRECTOR + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBContract.MovieEntry.TABLE_NAME;


    MovieDatabase(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        Log.v(TAG, "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
        Log.v(TAG, "onUpgrade");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
        Log.v(TAG, "onDowngrade");
    }
}

