package com.rizkyfadillah.popularmoviesstage1.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rizkyfadillah.popularmoviesstage1.PopularMoviesStage1App;
import com.rizkyfadillah.popularmoviesstage1.R;
import com.rizkyfadillah.popularmoviesstage1.ui.detail.DetailMovieActivity;
import com.rizkyfadillah.popularmoviesstage1.vo.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnClickMovieListener {

    @Inject
    MainViewModel viewmodel;

    private List<String> movieImageList;
    private List<Movie> movieList;
    private MovieAdapter movieAdapter;

    private int sort = -1;
    private int scrollPosition = -1;

    private final String STATE_SORT = "state_sort";
    private static final String STATE_SCROLL_POSITION = "state_scroll_position";

    @BindView(R.id.recylerview) RecyclerView recyclerView;
    @BindView(R.id.progressbar) ProgressBar progressBar;
    @BindView(R.id.layout_error) LinearLayout layoutError;
    @BindView(R.id.button_retry) Button btnRetry;
    @BindView(R.id.text_error_message) TextView txtErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        layoutError.setVisibility(View.GONE);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutError.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                showMovies("popular");
            }
        });

        movieList = new ArrayList<>();
        movieImageList = new ArrayList<>();

        movieAdapter = new MovieAdapter(this, movieImageList);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(movieAdapter);

        setupActivityComponent();

        if (savedInstanceState != null) {
            scrollPosition = savedInstanceState.getInt(STATE_SCROLL_POSITION);
            sort = savedInstanceState.getInt(STATE_SORT);
            switch (sort) {
                case R.id.top_rated:
                    showMovies("top_rated");
                    break;
                case R.id.popular:
                    showMovies("popular");
                    break;
                case R.id.favorite:
                    showMovies("favorite");
                    break;
            }
        } else {
            showMovies("popular");
            sort = R.id.popular;
        }
    }

    private void setupActivityComponent() {
        PopularMoviesStage1App.get()
                .getMainActivityComponent()
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

        if (sort == -1) {
            menu.findItem(R.id.popular).setChecked(true);
            return true;
        }

        menu.findItem(sort).setChecked(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        layoutError.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        switch (item.getItemId()) {
            case R.id.top_rated:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    sort = item.getItemId();
                    item.setChecked(true);
                    showMovies("top_rated");
                }
                return true;
            case R.id.popular:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    sort = item.getItemId();
                    item.setChecked(true);
                    showMovies("popular");
                }
                return true;
            case R.id.favorite:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    sort = item.getItemId();
                    item.setChecked(true);
                    showMovies("favorite");
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showMovies(String sort) {
        movieList.clear();
        movieImageList.clear();

        movieAdapter.notifyDataSetChanged();

        viewmodel.getMovies(sort)
                .subscribe(new DisposableObserver<List<Movie>>() {
                    @Override
                    public void onNext(List<Movie> movies) {
                        List<String> posterPaths = new ArrayList<>();
                        for (Movie movie : movies) {
                            posterPaths.add(movie.posterPath);
                        }
                        movieImageList.clear();
                        movieImageList.addAll(posterPaths);
                        movieList.clear();
                        movieList.addAll(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        layoutError.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        txtErrorMessage.setText(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                        movieAdapter.notifyDataSetChanged();
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setVerticalScrollbarPosition(scrollPosition);
                        recyclerView.scrollToPosition(scrollPosition);
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SORT, sort);
        outState.putInt(STATE_SCROLL_POSITION,
                ((GridLayoutManager) recyclerView.getLayoutManager())
                        .findFirstCompletelyVisibleItemPosition());

        super.onSaveInstanceState(outState);
    }
}
