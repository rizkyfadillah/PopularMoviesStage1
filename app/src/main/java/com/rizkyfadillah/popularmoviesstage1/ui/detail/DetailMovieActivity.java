package com.rizkyfadillah.popularmoviesstage1.ui.detail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rizkyfadillah.popularmoviesstage1.CustomLinearLayoutManager;
import com.rizkyfadillah.popularmoviesstage1.PopularMoviesStage1App;
import com.rizkyfadillah.popularmoviesstage1.R;
import com.rizkyfadillah.popularmoviesstage1.vo.Movie;
import com.rizkyfadillah.popularmoviesstage1.vo.Review;
import com.rizkyfadillah.popularmoviesstage1.vo.Video;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class DetailMovieActivity extends AppCompatActivity {

    @Inject
    DetailViewModel detailViewModel;

    @BindView(R.id.backdrop) ImageView backdrop;
    @BindView(R.id.poster_image) ImageView posterImage;
    @BindView(R.id.text_synopsis) TextView txtSynopsis;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.text_title) TextView txtTitle;
    @BindView(R.id.text_rating) TextView txtRating;
    @BindView(R.id.text_release_date) TextView txtReleaseDate;
    @BindView(R.id.text_vote_count) TextView txtVoteCount;
    @BindView(R.id.fab_favorite) FloatingActionButton fabFavorite;
    @BindView(R.id.recyclerview_trailer) RecyclerView recyclerViewTrailer;
    @BindView(R.id.recyclerview_review) RecyclerView recyclerViewReview;
    @BindView(R.id.text_label_no_review) TextView txtLabelNoReview;

    private List<Video> videoList = new ArrayList<>();
    private List<Review> reviewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        injectDependencies();

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getIntent().getExtras() != null) {
            final String posterPath = getIntent().getStringExtra("poster_path");
            final String backdropPath = getIntent().getStringExtra("backdrop_path");
            final String overview = getIntent().getStringExtra("overview");
            final String originalTitle = getIntent().getStringExtra("original_title");
            final String releaseDate = getIntent().getStringExtra("release_date");
            final double voteAverage = getIntent().getDoubleExtra("vote_average", 0);
            final int voteCount = getIntent().getIntExtra("vote_count", 0);
            final String id = getIntent().getStringExtra("id");

            setActionBarTitle(originalTitle);

            if (detailViewModel.isMovieFavorite(id)) {
                fabFavorite.setImageDrawable(getResources().getDrawable(R.drawable.octagon));
            }

            Picasso.with(this)
                    .load("http://image.tmdb.org/t/p/w780" + backdropPath)
                    .fit()
                    .centerCrop()
                    .into(backdrop);

            Picasso.with(this)
                    .load("http://image.tmdb.org/t/p/w780" + posterPath)
                    .placeholder(R.color.colorImagePlaceholder)
                    .into(posterImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            supportStartPostponedEnterTransition();
                        }

                        @Override
                        public void onError() {
                            supportPostponeEnterTransition();
                        }
                    });

            txtSynopsis.setText(overview);
            txtTitle.setText(originalTitle);
            txtRating.setText(String.valueOf(voteAverage));
            txtVoteCount.setText(String.valueOf(voteCount));
            txtReleaseDate.setText(getResources().getString(R.string.release_date, releaseDate));

            final VideoMovieAdapter videoMovieAdapter = new VideoMovieAdapter(videoList);
            recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(DetailMovieActivity.this));
            recyclerViewTrailer.setAdapter(videoMovieAdapter);

            final ReviewMovieAdapter reviewMovieAdapter = new ReviewMovieAdapter(reviewList);
            recyclerViewReview.setLayoutManager(new CustomLinearLayoutManager(DetailMovieActivity.this));
            recyclerViewReview.setAdapter(reviewMovieAdapter);

            detailViewModel.getMovieVideos(id)
                    .subscribe(new DisposableObserver<Video>() {
                        @Override
                        public void onNext(@NonNull Video video) {
                            videoList.add(video);
                            videoMovieAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Toast.makeText(DetailMovieActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                        }
                    });

            detailViewModel.getMovieReviews(id)
                    .subscribe(new DisposableObserver<Review>() {
                        @Override
                        public void onNext(@NonNull Review review) {
                            reviewList.add(review);
                            reviewMovieAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Toast.makeText(DetailMovieActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            Timber.d("onComplete review");
                            if (reviewList.isEmpty()) {
                                recyclerViewReview.setVisibility(View.GONE);
                                txtLabelNoReview.setVisibility(View.VISIBLE);
                            }
                        }
                    });

            fabFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Movie movie = new Movie();
                    movie.id = id;
                    movie.backdropPath = backdropPath;
                    movie.originalTitle = originalTitle;
                    movie.overview = overview;
                    movie.posterPath = posterPath;
                    movie.releaseDate = releaseDate;
                    movie.voteAverage = voteAverage;
                    movie.voteCount = voteCount;

                    if (detailViewModel.isMovieFavorite(id)) {
                        deleteFavoriteMovie(id);
                    } else {
                        saveFavoriteMovie2(movie);
                    }
                }
            });
        }
    }

    private void deleteFavoriteMovie(String id) {
        detailViewModel.deleteFavoriteMovie(id)
                .subscribe(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        if (aBoolean) {
                            fabFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_border_black_48dp));
                        } else {
                            Toast.makeText(DetailMovieActivity.this, "Delete favorite movie failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void saveFavoriteMovie(Movie movie) {
        detailViewModel.addFavoriteMovie(movie)
                .subscribe(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        if (aBoolean)
                            Toast.makeText(DetailMovieActivity.this, "Add favorite movie succeed", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(DetailMovieActivity.this, "Add favorite movie failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(DetailMovieActivity.this, "onError: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void saveFavoriteMovie2(Movie movie) {
        detailViewModel.addFavoriteMovie2(movie)
                .subscribe(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        if (aBoolean) {
                            Toast.makeText(DetailMovieActivity.this, "Add favorite movie succeed", Toast.LENGTH_SHORT).show();
                            fabFavorite.setImageDrawable(getResources().getDrawable(R.drawable.octagon));
                        } else {
                            Toast.makeText(DetailMovieActivity.this, "Add favorite movie failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(DetailMovieActivity.this, "onError: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void injectDependencies() {
        PopularMoviesStage1App.get()
                .getAppComponent()
                .plus(new DetailActivityModule())
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

    public void setActionBarTitle(String originalTitle) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(originalTitle);
        }
    }
}
