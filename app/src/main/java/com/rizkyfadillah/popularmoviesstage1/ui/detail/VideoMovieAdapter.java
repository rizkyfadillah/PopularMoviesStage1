package com.rizkyfadillah.popularmoviesstage1.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rizkyfadillah.popularmoviesstage1.R;
import com.rizkyfadillah.popularmoviesstage1.vo.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Rizky Fadillah on 17/07/2017.
 */

class VideoMovieAdapter extends RecyclerView.Adapter<VideoMovieAdapter.VideoViewHolder> {

    private final List<Video> videoList;

    private Context context;

    VideoMovieAdapter(List<Video> videoList) {
        this.videoList = videoList;
    }

    @Override
    public VideoMovieAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoMovieAdapter.VideoViewHolder holder, int position) {
        holder.bind(videoList.get(position));
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.imageview_video_thumbnail) ImageView ivVideoThumbnail;
        @BindView(R.id.text_video_title) TextView txtVideoTitle;

        private String key;

        VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void bind(Video video) {
            this.key = video.thumbnail;

            Picasso.with(context)
                    .load("https://img.youtube.com/vi/" + video.thumbnail + "/sddefault.jpg")
                    .into(ivVideoThumbnail);

            txtVideoTitle.setText(video.title);
        }

        @Override
        public void onClick(View v) {
            String url = "http://www.youtube.com/watch?v=" + key;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        }
    }

}
