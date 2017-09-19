package com.example.sandarumk.popularmovies.utilities;

import android.util.Log;

import com.example.sandarumk.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinu on 9/17/17.
 */

public class MoviesJsonUtils {

    private static final String TAG = MoviesJsonUtils.class.getSimpleName();

    private static final String JSON_TITLE = "title";
    private static final String JSON_ORIGINAL_TITLE = "original_title";
    private static final String JSON_OVERVIEW = "overview";
    private static final String JSON_VOTE_AVERAGE = "vote_average";
    private static final String JSON_RELEASE_DATE = "release_date";
    private static final String JSON_POSTER_PATH = "poster_path";
    private static final String JSON_RESULTS = "results";

    public static List<Movie> getMovies(String movieListJsonString) {
        List<Movie> movies = new ArrayList<>();
        Log.d(TAG, movieListJsonString);
        JSONObject moviesJson;
        try {
            moviesJson = new JSONObject(movieListJsonString);

            JSONArray resultsArray = moviesJson.getJSONArray(JSON_RESULTS);

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject movie = resultsArray.getJSONObject(i);
                String movieTitle = movie.getString(JSON_TITLE);
                String originalTitle = movie.getString(JSON_ORIGINAL_TITLE);
                String overview = movie.getString(JSON_OVERVIEW);
                String rating = movie.getString(JSON_VOTE_AVERAGE);
                String releaseDate = movie.getString(JSON_RELEASE_DATE);
                String posterPath = movie.getString(JSON_POSTER_PATH);
                Movie movieObject = new Movie(movieTitle, originalTitle, overview, rating, releaseDate, posterPath);
                Log.d(TAG, "Movie " + movieTitle + "Added");
                movies.add(movieObject);

            }
        } catch (JSONException e) {
            Log.e(TAG, "Unable to parse JSON", e);
        }

        return movies;

    }
}
