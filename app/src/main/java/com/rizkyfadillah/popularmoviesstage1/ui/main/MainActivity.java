package com.rizkyfadillah.popularmoviesstage1.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.rizkyfadillah.popularmoviesstage1.PopularMoviesStage1App;
import com.rizkyfadillah.popularmoviesstage1.R;
import com.rizkyfadillah.popularmoviesstage1.vo.Movie;
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

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<String> movieImageList;
    private List<Movie> movieList;
    private MovieAdapter movieAdapter;

    @BindView(R.id.recylerview) RecyclerView recyclerView;
    @BindView(R.id.progressbar) ProgressBar progressBar;

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

        showMovies("popular");
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
        intent.putExtra("poster_path", movieList.get(position).posterPath);
        intent.putExtra("id", movieList.get(position).id);
        intent.putExtra("original_title", movieList.get(position).originalTitle);
        intent.putExtra("overview", movieList.get(position).overview);
        intent.putExtra("release_date", movieList.get(position).releaseDate);
        intent.putExtra("vote_average", movieList.get(position).voteAverage);
        intent.putExtra("vote_count", movieList.get(position).voteCount);
        intent.putExtra("backdrop_path", movieList.get(position).backdropPath);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, imageView, "movieImage");

        startActivity(intent, options.toBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.popular).setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.top_rated:
                if (item.isChecked())
                    item.setChecked(false);
                else {
                    item.setChecked(true);
                    showMovies("top_rated");
                }
                return true;
            case R.id.popular:
                if (item.isChecked())
                    item.setChecked(false);
                else {
                    item.setChecked(true);
                    showMovies("popular");
                }
                return true;
            case R.id.favorite:
                if (item.isChecked())
                    item.setChecked(false);
                else {
                    item.setChecked(true);
                    showFavoriteMovies();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFavoriteMovies() {
        progressBar.setVisibility(View.VISIBLE);

        movieList.clear();
        movieImageList.clear();

        viewmodel.getFavoriteMovies()
                .subscribe(new ResourceObserver<Movie>() {
                    @Override
                    public void onNext(Movie movie) {
                        progressBar.setVisibility(View.GONE);
                        movieImageList.add(movie.posterPath);
                        movieList.add(movie);
                        movieAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void showMovies(String sort) {
        progressBar.setVisibility(View.VISIBLE);

        movieList.clear();
        movieImageList.clear();

        viewmodel.getMovies(sort)
                .subscribe(new ResourceObserver<Movie>() {
                    @Override
                    public void onNext(Movie movie) {
                        Log.d(TAG, movie.posterPath);
                        progressBar.setVisibility(View.GONE);
                        movieImageList.add(movie.posterPath);
                        movieList.add(movie);
                        movieAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

}
