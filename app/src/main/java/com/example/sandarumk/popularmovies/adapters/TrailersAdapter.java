package com.example.sandarumk.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sandarumk.popularmovies.R;
import com.example.sandarumk.popularmovies.Trailer;

import java.util.List;

/**
 * Created by dinu on 12/3/17.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder>  {


    @Override
    public TrailersAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new TrailersAdapter.TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailersAdapter.TrailerViewHolder holder, int position) {
        Trailer trailer = mMoviewTrailersList.get(position);
        Context context = holder.mImageTrailerView.getContext();
        holder.mTextViewTrailerName.setText(trailer.getTrailerName());
        holder.mTextViewTrailerName.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        if (mMoviewTrailersList == null) {
            return 0;
        }
        return mMoviewTrailersList.size();
    }

    private List<Trailer> mMoviewTrailersList;

    private final TrailersAdapter.TrailersAdapterOnClickHandler mTrailerAdapterOnClickHandler;

    public interface TrailersAdapterOnClickHandler {
        void onClick(Trailer trailer);
    }

    public TrailersAdapter(TrailersAdapter.TrailersAdapterOnClickHandler clickHandler) {
        mTrailerAdapterOnClickHandler = clickHandler;
    }

    public void setMovieData(List<Trailer> movieTrailers){
        mMoviewTrailersList = movieTrailers;
        notifyDataSetChanged();
    }


    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageTrailerView;
        private TextView mTextViewTrailerName;


        public TrailerViewHolder(View itemView) {
            super(itemView);
            mImageTrailerView = (ImageView) itemView.findViewById(R.id.iv_play_button);
            mTextViewTrailerName = (TextView) itemView.findViewById(R.id.tv_trailer_name) ;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
           Trailer trailer = mMoviewTrailersList.get(adapterPosition);
            mTrailerAdapterOnClickHandler.onClick(trailer);
        }
    }
}
