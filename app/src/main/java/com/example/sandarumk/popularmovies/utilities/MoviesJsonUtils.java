package com.example.sandarumk.popularmovies.utilities;

import android.util.Log;

import com.example.sandarumk.popularmovies.Movie;
import com.example.sandarumk.popularmovies.Review;
import com.example.sandarumk.popularmovies.Trailer;

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
    private static final String JSON_ID = "id";

    //for trailers
    private static final String JSON_NAME = "name";
    private static final String JSON_KEY = "key";

    //for reviews
    private static final String JSON_AUTHOR = "author";
    private static final String JSON_CONTENT = "content";


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
                String id = movie.getString(JSON_ID);
                Movie movieObject = new Movie(movieTitle, originalTitle, overview, rating, releaseDate, posterPath,id);
                Log.d(TAG, "Movie " + movieTitle + "Added");
                movies.add(movieObject);

            }
        } catch (JSONException e) {
            Log.e(TAG, "Unable to parse JSON", e);
        }

        return movies;

    }

    public static List<Trailer> getTrailers(String trailersURL) {
        List<Trailer> trailers = new ArrayList<>();
        Log.d(TAG, trailersURL);
        JSONObject trailersJSON;
        try {
            trailersJSON = new JSONObject(trailersURL);

            JSONArray resultsArray = trailersJSON.getJSONArray(JSON_RESULTS);

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject trailer = resultsArray.getJSONObject(i);
                String key = trailer.getString(JSON_KEY);
                String name = trailer.getString(JSON_NAME);
                String id = trailer.getString(JSON_ID);
                Trailer trailerObject = new Trailer(key,name,id);
                Log.d(TAG, "Movie Trailer " + name + "Added");
                trailers.add(trailerObject);

            }
        } catch (JSONException e) {
            Log.e(TAG, "Unable to parse JSON", e);
        }
        return trailers;
    }

    public static List<Review> getReviews(String reviewsURL) {
        List<Review> reviews = new ArrayList<>();
        Log.d(TAG, reviewsURL);
        JSONObject reviewsJSON;
        try {
            reviewsJSON = new JSONObject(reviewsURL);

            JSONArray resultsArray = reviewsJSON.getJSONArray(JSON_RESULTS);

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject review = resultsArray.getJSONObject(i);
                String author = review.getString(JSON_AUTHOR);
                String content = review.getString(JSON_CONTENT);
                String id = review.getString(JSON_ID);
                Review reviewObject = new Review(id,content,author);
                Log.d(TAG, "Movie Review By " + author + "Added");
                reviews.add(reviewObject);

            }
        } catch (JSONException e) {
            Log.e(TAG, "Unable to parse JSON", e);
        }
        return reviews;
    }
}
