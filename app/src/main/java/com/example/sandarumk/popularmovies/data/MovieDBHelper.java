package com.example.sandarumk.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dinu on 12/5/17.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 7;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_FAVOURITE_MOVIES_TABLE = "CREATE TABLE "+ MoviesContract.FavouriteMovies.TABLE_NAME +
                " ( " + MoviesContract.FavouriteMovies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesContract.FavouriteMovies.COLUMN_NAME_MOVIE_ID + " TEXT NOT NULL, " +
                MoviesContract.FavouriteMovies.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                MoviesContract.FavouriteMovies.COLUMN_NAME_MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                MoviesContract.FavouriteMovies.COLUMN_NAME_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                MoviesContract.FavouriteMovies.COLUMN_NAME_PLOT_SNYPNOSYS + " TEXT NOT NULL, " +
                MoviesContract.FavouriteMovies.COLUMN_NAME_RATING + " TEXT NOT NULL, " +
                MoviesContract.FavouriteMovies.COLUMN_NAME_RELEASE_DATE + " TEXT NOT NULL" +
                ");";
        db.execSQL(CREATE_TABLE_FAVOURITE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.FavouriteMovies.TABLE_NAME);
        onCreate(db);

    }
}
