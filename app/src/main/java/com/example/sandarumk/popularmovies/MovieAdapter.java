package com.example.sandarumk.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sandarumk.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dinu on 9/17/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> mMovieNameList;

    private final MovieAdapterOnClickHandler mMoviewAdapterOnClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mMoviewAdapterOnClickHandler = clickHandler;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new MovieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        Movie movie = mMovieNameList.get(position);
        Context context = holder.mImageView.getContext();
        Picasso.with(context).load(NetworkUtils.MOVIE_DB_IMAGE_BASE_URL+movie.getPosterPath()).into(holder.mImageView);
    }


    @Override
    public int getItemCount() {
        if (mMovieNameList == null) {
            return 0;
        }
        return mMovieNameList.size();
    }

    public void setMovieData(List<Movie> movieNames){
        mMovieNameList = movieNames;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;


        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movieName = mMovieNameList.get(adapterPosition);
            mMoviewAdapterOnClickHandler.onClick(movieName);
        }
    }
}
