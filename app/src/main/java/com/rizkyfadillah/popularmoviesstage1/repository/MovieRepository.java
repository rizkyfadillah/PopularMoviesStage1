package com.rizkyfadillah.popularmoviesstage1.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rizkyfadillah.popularmoviesstage1.BuildConfig;
import com.rizkyfadillah.popularmoviesstage1.MovieDBService;
import com.rizkyfadillah.popularmoviesstage1.api.BaseApiResponse;
import com.rizkyfadillah.popularmoviesstage1.vo.Movie;
import com.rizkyfadillah.popularmoviesstage1.db.MovieContract;
import com.rizkyfadillah.popularmoviesstage1.vo.Video;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */

public class MovieRepository {

    private MovieDBService service;
    private SQLiteDatabase database;

    public MovieRepository(MovieDBService service, SQLiteDatabase database) {
        this.service = service;
        this.database = database;
    }

    public Observable<Movie> getMovies(String sort) {
        return service.getMovies(sort, BuildConfig.THE_MOVIE_DB_API_TOKEN)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(new Function<BaseApiResponse<List<Movie>>, Iterable<? extends Movie>>() {
                    @Override
                    public Iterable<? extends Movie> apply(BaseApiResponse<List<Movie>> response) throws Exception {
                        return response.results;
                    }
                });
    }

    public Observable<Video> getMovieVideos(String id) {
        return service.getMovieVideos(id, BuildConfig.THE_MOVIE_DB_API_TOKEN)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(new Function<BaseApiResponse<List<Video>>, Iterable<? extends Video>>() {
                    @Override
                    public Iterable<? extends Video> apply(@NonNull BaseApiResponse<List<Video>> response) throws Exception {
                        return response.results;
                    }
                });
    }

    public Observable<Long> addFavoriteMovie(final Movie movie) {
        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, movie.originalTitle);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER, movie.posterPath);
                e.onNext(database.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues));
            }
        });
    }

    public Observable<Movie> getFavoriteMovies() {
        final Cursor cursor = database.query(
                MovieContract.MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        return Observable.create(new ObservableOnSubscribe<Movie>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Movie> e) throws Exception {
                if (cursor.moveToNext()) {
                    Movie movie = new Movie();
                    movie.posterPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER));
                    e.onNext(movie);
                }
            }
        });
    }
}
