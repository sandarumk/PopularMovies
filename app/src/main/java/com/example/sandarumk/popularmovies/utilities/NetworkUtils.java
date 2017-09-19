package com.example.sandarumk.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.sandarumk.popularmovies.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by dinu on 9/17/17.
 */

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static final String MOVIE_DB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    private static final String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie/";

    private final static String SORTING_ORDER_POPULAR = "popular";
    private final static String SORTING_ORDER_TOP_RATED = "top_rated";
    private final static String API_KEY = "api_key";
    private final static String LANUGUAGE = "language";
    private final static String DELIMITER = "\\A";


    public static URL buildUrl(Context context, int choice) {
        URL url = null;
        String sortingOrder = SORTING_ORDER_TOP_RATED;
        if (choice == 0) {
            sortingOrder = SORTING_ORDER_POPULAR;
        }
        Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                .appendPath(sortingOrder)
                .appendQueryParameter(API_KEY, context.getString(R.string.movie_db_api_key))
                .appendQueryParameter(LANUGUAGE, context.getString(R.string.language_en_us))
                .build();
        try {
            url = new URL(builtUri.toString());
            Log.d(TAG, url.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, e.toString());
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter(DELIMITER);

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
