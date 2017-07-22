package com.rizkyfadillah.popularmoviesstage1;

import com.rizkyfadillah.popularmoviesstage1.api.BaseApiResponse;
import com.rizkyfadillah.popularmoviesstage1.vo.Movie;
import com.rizkyfadillah.popularmoviesstage1.vo.Review;
import com.rizkyfadillah.popularmoviesstage1.vo.Video;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */

public interface MovieDBService {

    @GET("3/movie/{sort}")
    Observable<BaseApiResponse<List<Movie>>> getMovies(@Path("sort") String sort);

    @GET("3/movie/{id}/videos")
    Observable<BaseApiResponse<List<Video>>> getMovieVideos(@Path("id") String id);

    @GET("3/movie/{id}/reviews")
    Observable<BaseApiResponse<List<Review>>> getMovieReviews(@Path("id") String id);

}
