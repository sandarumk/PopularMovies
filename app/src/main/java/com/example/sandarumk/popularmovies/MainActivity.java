package com.example.sandarumk.popularmovies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.sandarumk.popularmovies.utilities.MoviesJsonUtils;
import com.example.sandarumk.popularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MovieAdapter mMovieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        RecyclerView mRecyclerView;

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_launcher_view);
        GridLayoutManager layoutManager
                = new GridLayoutManager(getApplicationContext(),2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();
        fetchMoviesTask.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mi_settings:
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                int checkedItem = sharedPref.getInt(Constants.SORT_ORDER, Constants.SORT_ORDER_POPULAR);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.dialog_title_sort_order_selecter)
                        .setSingleChoiceItems(R.array.sort_types,checkedItem,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putInt(Constants.SORT_ORDER, which);
                                editor.apply();
                                dialog.dismiss();
                                FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();
                                fetchMoviesTask.execute();

                            }

                        });

                builder.create().show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(Constants.TITLE,movie.getTitle());
        intent.putExtra(Constants.ORIGINAL_TITLE,movie.getOriginalTitle());
        intent.putExtra(Constants.RELEASE_DATE,movie.getReleaseDate());
        intent.putExtra(Constants.POSTER_PATH,movie.getPosterPath());
        intent.putExtra(Constants.OVERVIEW,movie.getPlotSypnosis());
        intent.putExtra(Constants.USER_RATING,movie.getRating());
        startActivity(intent);
    }

    public class FetchMoviesTask extends AsyncTask<Void, Void, List<Movie>>{

        @Override
        protected List<Movie> doInBackground(Void... params) {

            List<Movie> movieData = null;
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            int checkedItem = sharedPref.getInt(Constants.SORT_ORDER, Constants.SORT_ORDER_POPULAR);
            Log.d(TAG, "Selected Choice: "+checkedItem);
            try {
                String movieDBResponse = NetworkUtils
                        .getResponseFromHttpUrl(NetworkUtils.buildUrl(getApplicationContext(),checkedItem));
                movieData = MoviesJsonUtils.getMovies(movieDBResponse);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieData;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (movies != null) {
                mMovieAdapter.setMovieData(movies);
            }
        }
    }
}
