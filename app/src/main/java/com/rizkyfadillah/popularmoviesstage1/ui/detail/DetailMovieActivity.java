package com.rizkyfadillah.popularmoviesstage1.ui.detail;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.rizkyfadillah.popularmoviesstage1.PopularMoviesStage1App;
import com.rizkyfadillah.popularmoviesstage1.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    @Inject
    DetailMovieViewModel viewmodel;

    @BindView(R.id.backdrop) ImageView backdrop;
    @BindView(R.id.poster_image) ImageView posterImage;
    @BindView(R.id.text_synopsis) TextView txtSynopsis;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.text_title) TextView txtTitle;
    @BindView(R.id.text_rating) TextView txtRating;
    @BindView(R.id.text_release_date) TextView txtReleaseDate;
    @BindView(R.id.text_vote_count) TextView txtVoteCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ButterKnife.bind(this);

        setupActivityComponent();

        String id = getIntent().getStringExtra("id");
        String posterPath = getIntent().getStringExtra("poster_path");
        String backdropPath = getIntent().getStringExtra("backdrop_path");
        String overview = getIntent().getStringExtra("overview");
        String originalTitle = getIntent().getStringExtra("original_title");
        String releaseDate = getIntent().getStringExtra("release_date");
        double voteAverage = getIntent().getDoubleExtra("vote_average", 0);
        double voteCount = getIntent().getIntExtra("vote_count", 0);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(originalTitle);
        }

//        toolbar.setTitle(originalTitle);

        Picasso.with(this)
                .load("http://image.tmdb.org/t/p/w780" + backdropPath)
                .fit()
                .centerCrop()
                .into(backdrop);

        Picasso.with(this)
                .load("http://image.tmdb.org/t/p/w780" + posterPath)
                .into(posterImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError() {
                        supportStartPostponedEnterTransition();
                    }
                });

        txtSynopsis.setText(overview);
        txtTitle.setText(originalTitle);
        txtRating.setText(String.valueOf(voteAverage));
        txtVoteCount.setText(String.valueOf(voteCount));
        txtReleaseDate.setText(getResources().getString(R.string.release_date, releaseDate));
    }

    private void setupActivityComponent() {
        PopularMoviesStage1App.get()
                .getAppComponent()
                .plus(new DetailMovieActivityModule())
                .inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
