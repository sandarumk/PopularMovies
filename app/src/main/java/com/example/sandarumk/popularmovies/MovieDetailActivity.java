package com.example.sandarumk.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sandarumk.popularmovies.adapters.ReviewAdapter;
import com.example.sandarumk.popularmovies.adapters.TrailersAdapter;
import com.example.sandarumk.popularmovies.data.MovieDBHelper;
import com.example.sandarumk.popularmovies.data.MoviesContract;
import com.example.sandarumk.popularmovies.utilities.MoviesJsonUtils;
import com.example.sandarumk.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity implements TrailersAdapter.TrailersAdapterOnClickHandler{

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private TrailersAdapter mTrailersAdapter;
    private ReviewAdapter mReviewAdapter;
    private boolean isfavourite;
    private TextView favouriteStar;
    private SQLiteDatabase db;
    private String favouriteMovieID;
    private String favouriteMoviePoster;
    private String favouriteMovieRating;
    private String favouriteMovieTitle;
    private String facouriteMovieOriginalTitle;
    private String favouriteMoviePlotSypnosis;
    private String favouriteMoviereleaseDate;
    private boolean isfavouriteInDB;

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

        favouriteStar = (TextView) findViewById(R.id.ib_star_fav);

        MovieDBHelper dbHelper = new MovieDBHelper(this);
        db = dbHelper.getWritableDatabase();

        String originalTitle;
        String posterPath;
        String releaseDate;
        String rating;
        String overview;
        String id;
        String title;

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Constants.TITLE)) {
                title = intentThatStartedThisActivity.getStringExtra(Constants.TITLE);
                Log.d(TAG,"Title: "+title);
                favouriteMovieTitle= title;
            }
            if (intentThatStartedThisActivity.hasExtra(Constants.POSTER_PATH)) {
                posterPath = intentThatStartedThisActivity.getStringExtra(Constants.POSTER_PATH);
                Log.d(TAG,"Poster Path: "+posterPath);
                Picasso.with(getApplicationContext()).load(NetworkUtils.MOVIE_DB_IMAGE_BASE_URL+posterPath).into(movieThumbNail);
                favouriteMoviePoster = posterPath;
            }
            if (intentThatStartedThisActivity.hasExtra(Constants.ORIGINAL_TITLE)) {
                originalTitle = intentThatStartedThisActivity.getStringExtra(Constants.ORIGINAL_TITLE);
                Log.d(TAG,"Original Title: "+originalTitle);
                tvoriginalTitle.setText(originalTitle);
                facouriteMovieOriginalTitle = originalTitle;
            }
            if (intentThatStartedThisActivity.hasExtra(Constants.RELEASE_DATE)) {
                releaseDate = intentThatStartedThisActivity.getStringExtra(Constants.RELEASE_DATE);
                Log.d(TAG,"Release Date: "+releaseDate);
                tvrelaseDate.setText(releaseDate);
                favouriteMoviereleaseDate = releaseDate;
            }
            if (intentThatStartedThisActivity.hasExtra(Constants.OVERVIEW)) {
                overview = intentThatStartedThisActivity.getStringExtra(Constants.OVERVIEW);
                Log.d(TAG,"Plot Synopsis: "+overview);
                synopsis.setText(overview);
                favouriteMoviePlotSypnosis = overview;
            }
            if (intentThatStartedThisActivity.hasExtra(Constants.USER_RATING)) {
                rating = intentThatStartedThisActivity.getStringExtra(Constants.USER_RATING);
                Log.d(TAG,"User Rating: "+rating);
                userRating.setText(String.format(getString(R.string.rating),rating));
                favouriteMovieRating = rating;
            }
            if (intentThatStartedThisActivity.hasExtra(Constants.ID)) {
                id = intentThatStartedThisActivity.getStringExtra(Constants.ID);
                Log.d(TAG,"Trailer Movie ID: "+id);
                //fetch the trailer given the movie id

                RecyclerView recyclerViewTrailers;

                recyclerViewTrailers = (RecyclerView) findViewById(R.id.rv_trailer_view);
                RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
                recyclerViewTrailers.setLayoutManager(lm);

                mTrailersAdapter = new TrailersAdapter(this);
                recyclerViewTrailers.setAdapter(mTrailersAdapter);

                FetchMovieTrailers fv = new FetchMovieTrailers();
                fv.execute(id);

                RecyclerView recyclerViewReviews;

                recyclerViewReviews = (RecyclerView) findViewById(R.id.rv_review_view);
                RecyclerView.LayoutManager lm2 = new LinearLayoutManager(this);
                recyclerViewReviews.setLayoutManager(lm2);

                mReviewAdapter = new ReviewAdapter();
                recyclerViewReviews.setAdapter(mReviewAdapter);

                FetchReviews fr = new FetchReviews();
                fr.execute(id);

                favouriteMovieID =id;

                if(isFavourite(id)){
                    isfavourite = true;
                    favouriteStar.setTextColor(Color.parseColor("#FFD600"));
                    isfavouriteInDB = true;
                }

            }
        }
        setTitle(R.string.movie_detail_activity_title);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isfavourite && !isfavouriteInDB){
            ContentValues cv = new ContentValues();
            cv.put(MoviesContract.FavouriteMovies.COLUMN_NAME_MOVIE_ID,favouriteMovieID);
            cv.put(MoviesContract.FavouriteMovies.COLUMN_NAME_MOVIE_POSTER_PATH,favouriteMoviePoster);
            cv.put(MoviesContract.FavouriteMovies.COLUMN_NAME_ORIGINAL_TITLE,facouriteMovieOriginalTitle);
            cv.put(MoviesContract.FavouriteMovies.COLUMN_NAME_TITLE,favouriteMovieTitle);
            cv.put(MoviesContract.FavouriteMovies.COLUMN_NAME_RELEASE_DATE,favouriteMoviereleaseDate);
            cv.put(MoviesContract.FavouriteMovies.COLUMN_NAME_PLOT_SNYPNOSYS,favouriteMoviePlotSypnosis);
            cv.put(MoviesContract.FavouriteMovies.COLUMN_NAME_RATING,favouriteMovieRating);
            long rowId = db.insert(MoviesContract.FavouriteMovies.TABLE_NAME,null,cv);
            System.out.println(rowId);
        }
        if(!isfavourite && isfavouriteInDB){
            db.delete(MoviesContract.FavouriteMovies.TABLE_NAME, MoviesContract.FavouriteMovies.COLUMN_NAME_MOVIE_ID +" = "+ favouriteMovieID, null);
        }
    }

    private boolean isFavourite(String id) {
        Cursor cursor= db.query(MoviesContract.FavouriteMovies.TABLE_NAME,null, MoviesContract.FavouriteMovies.COLUMN_NAME_MOVIE_ID + "= ?",new String[]{id},null,null,null);
        return cursor.moveToFirst();
    }


    public void onToggleStar(View view){
        if(!isfavourite){
            isfavourite = true;
            favouriteStar.setTextColor(Color.parseColor("#FFD600"));
        }else{
            isfavourite = false;
            favouriteStar.setTextColor(Color.parseColor("#9E9E9E"));
        }

    }



    @Override
    public void onClick(Trailer trailer) {
        Context context = this;
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + trailer.getTrailerURL()));
        startActivity(webIntent);
    }

    public class FetchMovieTrailers extends AsyncTask<String,Void, List<Trailer>>{

        @Override
        protected List<Trailer> doInBackground(String... params) {
            String movieId = params[0];
            List<Trailer> moviewTrailersList;
            try {
                String trailersURL = NetworkUtils
                        .getResponseFromHttpUrl(NetworkUtils.buildTrailerURL(getApplicationContext(),movieId));
                moviewTrailersList = MoviesJsonUtils.getTrailers(trailersURL);
                for(Trailer trailer: moviewTrailersList){
                    Log.d(TAG,trailer.getTrailerName());
                }

                return moviewTrailersList;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Trailer> trailers) {
            if (trailers != null) {
                mTrailersAdapter.setMovieData(trailers);
            }
        }
    }

    public class FetchReviews extends AsyncTask<String,Void, List<Review>>{

        @Override
        protected List<Review> doInBackground(String... params) {
            String movieId = params[0];
            List<Review> movieReviewList;
            try {
                String reviewURL = NetworkUtils
                        .getResponseFromHttpUrl(NetworkUtils.buildReviewURL(getApplicationContext(),movieId));
                movieReviewList = MoviesJsonUtils.getReviews(reviewURL);
                for(Review review: movieReviewList){
                   Log.d(TAG,review.getAuthor());
                }
                return movieReviewList;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Review> reviews) {
            if (reviews != null) {
                mReviewAdapter.setReviewData(reviews);
            }
        }
    }
}
