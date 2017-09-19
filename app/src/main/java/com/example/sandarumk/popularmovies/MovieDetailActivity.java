package com.example.sandarumk.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sandarumk.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intentThatStartedThisActivity = getIntent();

        ImageView movieThumbNail = (ImageView) findViewById(R.id.iv_movie_thumbnail);

        TextView tvoriginalTitle = (TextView) findViewById(R.id.tv_original_title);

        TextView tvrelaseDate = (TextView) findViewById(R.id.tv_release_date);

        TextView synopsis = (TextView) findViewById(R.id.tv_overview) ;

        TextView userRating = (TextView) findViewById(R.id.tv_user_rating) ;

        String originalTitle;
        String posterPath;
        String releaseDate;
        String rating;
        String overview;

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Constants.POSTER_PATH)) {
                posterPath = intentThatStartedThisActivity.getStringExtra(Constants.POSTER_PATH);
                Log.d(TAG,"Poster Path: "+posterPath);
                Picasso.with(getApplicationContext()).load(NetworkUtils.MOVIE_DB_IMAGE_BASE_URL+posterPath).into(movieThumbNail);
            }
            if (intentThatStartedThisActivity.hasExtra(Constants.ORIGINAL_TITLE)) {
                originalTitle = intentThatStartedThisActivity.getStringExtra(Constants.ORIGINAL_TITLE);
                Log.d(TAG,"Original Title: "+originalTitle);
                tvoriginalTitle.setText(originalTitle);
            }
            if (intentThatStartedThisActivity.hasExtra(Constants.RELEASE_DATE)) {
                releaseDate = intentThatStartedThisActivity.getStringExtra(Constants.RELEASE_DATE);
                Log.d(TAG,"Release Date: "+releaseDate);
                tvrelaseDate.setText(releaseDate);
            }
            if (intentThatStartedThisActivity.hasExtra(Constants.OVERVIEW)) {
                overview = intentThatStartedThisActivity.getStringExtra(Constants.OVERVIEW);
                Log.d(TAG,"Plot Synopsis: "+overview);
                synopsis.setText(overview);
            }
            if (intentThatStartedThisActivity.hasExtra(Constants.USER_RATING)) {
                rating = intentThatStartedThisActivity.getStringExtra(Constants.USER_RATING);
                Log.d(TAG,"User Rating: "+rating);
                userRating.setText(String.format(getString(R.string.rating),rating));
            }
        }
        setTitle(R.string.movie_detail_activity_title);
    }
}
