package com.rizkyfadillah.popularmoviesstage1.repository;

import com.rizkyfadillah.popularmoviesstage1.Constants;
import com.rizkyfadillah.popularmoviesstage1.MovieDBService;
import com.rizkyfadillah.popularmoviesstage1.api.BaseApiResponse;
import com.rizkyfadillah.popularmoviesstage1.api.MovieResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */

public class MovieRepository {

    private MovieDBService service;

    public MovieRepository(MovieDBService service) {
        this.service = service;
    }

    public Observable<MovieResponse> getPopularMovies() {
        return service.getPopularMovies(Constants.API_KEY)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(new Function<BaseApiResponse<List<MovieResponse>>, Iterable<? extends MovieResponse>>() {
                    @Override
                    public Iterable<? extends MovieResponse> apply(BaseApiResponse<List<MovieResponse>> response) throws Exception {
                        return response.results;
                    }
                });
    }

    public Observable<MovieResponse> getMovieDetail() {
        return service.getMovieDetail(Constants.API_KEY)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
