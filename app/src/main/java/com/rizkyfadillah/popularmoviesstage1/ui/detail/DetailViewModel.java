package com.rizkyfadillah.popularmoviesstage1.ui.detail;

import android.net.Uri;

import com.rizkyfadillah.popularmoviesstage1.vo.Movie;
import com.rizkyfadillah.popularmoviesstage1.repository.MovieRepository;
import com.rizkyfadillah.popularmoviesstage1.vo.Review;
import com.rizkyfadillah.popularmoviesstage1.vo.Video;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author Rizky Fadillah on 17/07/2017.
 */

public class DetailViewModel {

    private final MovieRepository movieRepository;

    public DetailViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    Observable<Boolean> addFavoriteMovie2(Movie movie) {
        return movieRepository.addFavoriteMovie2(movie)
                .map(new Function<Uri, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull Uri uri) throws Exception {
                        return uri != null;
                    }
                });
    }

    Observable<Video> getMovieVideos(String id) {
        return movieRepository.getMovieVideos(id);
    }

    Observable<Review> getMovieReviews(String id) {
        return movieRepository.getMovieReviews(id);
    }

    boolean isMovieFavorite(String id) {
        return movieRepository.findMovieById(id) != null;
    }

    Observable<Boolean> deleteFavoriteMovie(final String id) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                e.onNext(movieRepository.deleteMovieById(id) != 0);
                e.onComplete();
            }
        });
    }
}
