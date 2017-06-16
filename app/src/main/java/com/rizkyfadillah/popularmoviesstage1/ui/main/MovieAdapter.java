package com.rizkyfadillah.popularmoviesstage1.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rizkyfadillah.popularmoviesstage1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private List<String> urlImageList;

    private Context mContext;

    private OnClickMovieListener mCallback;

    public MovieAdapter(OnClickMovieListener callback, List<String> urlImageList) {
        this.urlImageList = urlImageList;
        mCallback = callback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(urlImageList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return urlImageList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageMovie;
        private int mPosition;

        MyViewHolder(View itemView) {
            super(itemView);
            imageMovie = (ImageView) itemView.findViewById(R.id.imageview);
            imageMovie.setOnClickListener(this);
        }

        void bind(String imageUrl, int position) {
            mPosition = position;
            Picasso.with(mContext)
                    .load("http://image.tmdb.org/t/p/w185" + imageUrl)
                    .into(imageMovie);
        }

        @Override
        public void onClick(View v) {
            mCallback.onClickMovie(mPosition, imageMovie);
        }
    }

    interface OnClickMovieListener {
        void onClickMovie(int position, ImageView imageView);
    }

}