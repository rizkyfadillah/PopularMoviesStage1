package com.rizkyfadillah.popularmoviesstage1.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.rizkyfadillah.popularmoviesstage1.PopularMoviesStage1App;
import com.rizkyfadillah.popularmoviesstage1.R;
import com.rizkyfadillah.popularmoviesstage1.api.MovieResponse;
import com.rizkyfadillah.popularmoviesstage1.ui.detail.DetailMovieActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.ResourceObserver;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnClickMovieListener {

    @Inject
    MainViewModel viewmodel;

    private List<String> movieImageList;
    private List<MovieResponse> movieList;
    private MovieAdapter movieAdapter;

    @BindView(R.id.recylerview) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        movieList = new ArrayList<>();
        movieImageList = new ArrayList<>();

        movieAdapter = new MovieAdapter(this, movieImageList);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(movieAdapter);

        setupActivityComponent();

        bind();
    }

    private void bind() {
        viewmodel.getPopularMovies()
                .subscribe(new ResourceObserver<MovieResponse>() {
                    @Override
                    public void onNext(MovieResponse movie) {
                        movieImageList.add(movie.poster_path);
                        movieList.add(movie);
                        movieAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setupActivityComponent() {
        PopularMoviesStage1App.get()
                .getAppComponent()
                .plus(new MainActivityModule())
                .inject(this);
    }

    @Override
    public void onClickMovie(int position, ImageView imageView) {
        Intent intent = new Intent(this, DetailMovieActivity.class);
        intent.putExtra("poster_path", movieList.get(position).poster_path);
        intent.putExtra("id", movieList.get(position).id);
        intent.putExtra("original_title", movieList.get(position).original_title);
        intent.putExtra("overview", movieList.get(position).overview);
        intent.putExtra("release_date", movieList.get(position).release_date);
        intent.putExtra("vote_average", movieList.get(position).vote_average);
        intent.putExtra("vote_count", movieList.get(position).vote_count);
        intent.putExtra("backdrop_path", movieList.get(position).backdrop_path);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, imageView, "movieImage");

        startActivity(intent, options.toBundle());
    }
}
