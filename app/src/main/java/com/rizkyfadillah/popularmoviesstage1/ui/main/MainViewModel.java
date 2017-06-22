package com.rizkyfadillah.popularmoviesstage1.ui.main;

import com.rizkyfadillah.popularmoviesstage1.api.MovieResponse;
import com.rizkyfadillah.popularmoviesstage1.repository.MovieRepository;

import io.reactivex.Observable;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */

public class MainViewModel {

    private MovieRepository movieRepository;

    MainViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    Observable<MovieResponse> getMovies(String sort) {
        return movieRepository.getMovies(sort);
    }

}
