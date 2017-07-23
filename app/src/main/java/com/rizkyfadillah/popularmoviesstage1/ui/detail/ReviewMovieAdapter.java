package com.rizkyfadillah.popularmoviesstage1.ui.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rizkyfadillah.popularmoviesstage1.R;
import com.rizkyfadillah.popularmoviesstage1.vo.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Rizky Fadillah on 22/07/2017.
 */

class ReviewMovieAdapter extends RecyclerView.Adapter<ReviewMovieAdapter.ReviewViewHoler> {

    private final List<Review> reviewList;

    ReviewMovieAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public ReviewViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_review, parent, false);
        return new ReviewViewHoler(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHoler holder, int position) {
        holder.bind(reviewList.get(position));
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class ReviewViewHoler extends RecyclerView.ViewHolder {
        @BindView(R.id.text_author) TextView tvAuthor;
        @BindView(R.id.text_content) TextView tvContent;

        ReviewViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Review review) {
            tvAuthor.setText(review.author);
            tvContent.setText(review.content);
        }
    }

}
