package com.rizkyfadillah.popularmoviesstage1.ui.detail;

import com.rizkyfadillah.popularmoviesstage1.api.MovieResponse;
import com.rizkyfadillah.popularmoviesstage1.repository.MovieRepository;

import io.reactivex.Observable;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */

public class DetailMovieViewModel {

    private Observable<MovieResponse> detailMovie;

    public DetailMovieViewModel(MovieRepository movieRepository) {
        detailMovie = movieRepository.getMovieDetail();
    }

    public Observable<MovieResponse> getDetailMovie() {
        return detailMovie;
    }

}
