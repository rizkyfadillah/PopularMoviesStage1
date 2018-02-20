package com.rizkyfadillah.popularmoviesstage1.ui.main;

import com.rizkyfadillah.popularmoviesstage1.vo.Movie;
import com.rizkyfadillah.popularmoviesstage1.repository.MovieRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */

public class MainViewModel {

    private final MovieRepository movieRepository;

    @Inject
    public MainViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Observable<List<Movie>> getMovies(String sort) {
        return movieRepository.getMovies(sort);
    }

}
