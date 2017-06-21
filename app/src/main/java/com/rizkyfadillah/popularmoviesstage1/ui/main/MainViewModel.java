package com.rizkyfadillah.popularmoviesstage1.ui.main;

import com.rizkyfadillah.popularmoviesstage1.api.MovieResponse;
import com.rizkyfadillah.popularmoviesstage1.repository.MovieRepository;

import io.reactivex.Observable;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */

public class MainViewModel {

    private Observable<MovieResponse> popularMovies;
    private Observable<MovieResponse> topRatedMovies;

    MainViewModel(MovieRepository movieRepository) {
        popularMovies = movieRepository.getPopularMovies();
        topRatedMovies = movieRepository.getTopRatedMovies();
    }

    Observable<MovieResponse> getPopularMovies() {
        return popularMovies;
    }

    public Observable<MovieResponse> getTopRatedMovies() {
        return topRatedMovies;
    }

}
