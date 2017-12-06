package com.example.sandarumk.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sandarumk.popularmovies.R;
import com.example.sandarumk.popularmovies.Review;

import java.util.List;

/**
 * Created by dinu on 12/3/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>  {


    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new ReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewViewHolder holder, int position) {
        Review review = mMovieReviewList.get(position);
        Context context = holder.mContentTextView.getContext();
        holder.mAuthorTextView.setText(review.getAuthor());
        holder.mContentTextView.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        if (mMovieReviewList == null) {
            return 0;
        }
        return mMovieReviewList.size();
    }

    private List<Review> mMovieReviewList;


    public void setReviewData(List<Review> movieReviews){
        mMovieReviewList = movieReviews;
        notifyDataSetChanged();
    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView mAuthorTextView;
        private TextView mContentTextView;


        public ReviewViewHolder(View itemView) {
            super(itemView);
            mAuthorTextView = (TextView) itemView.findViewById(R.id.tv_author);
            mContentTextView = (TextView) itemView.findViewById(R.id.tc_review_content);
        }

    }
}
