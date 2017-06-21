package com.rizkyfadillah.popularmoviesstage1;

import com.rizkyfadillah.popularmoviesstage1.api.BaseApiResponse;
import com.rizkyfadillah.popularmoviesstage1.api.MovieResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */

public interface MovieDBService {

    @GET("3/movie/popular")
    Observable<BaseApiResponse<List<MovieResponse>>> getPopularMovies(@Query("api_key") String apiKey);

    @GET("3/movie/top_rated")
    Observable<BaseApiResponse<List<MovieResponse>>> getTopRatedMovies(@Query("api_key") String apiKey);

}
