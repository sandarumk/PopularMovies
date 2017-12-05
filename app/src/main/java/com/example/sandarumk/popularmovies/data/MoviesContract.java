package com.example.sandarumk.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dinu on 12/5/17.
 */

public final class MoviesContract {

    private MoviesContract(){}

    public static final String AUTHORITY = "com.example.sandarumk.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static class FavouriteMovies implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "favourite_movies";
        public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_NAME_PLOT_SNYPNOSYS = "plot_sypnosys";
        public static final String COLUMN_NAME_RATING= "rating";
        public static final String COLUMN_NAME_MOVIE_POSTER_PATH = "poster_path";
        public static final String COLUMN_NAME_RELEASE_DATE = "release_date";


    }


}
