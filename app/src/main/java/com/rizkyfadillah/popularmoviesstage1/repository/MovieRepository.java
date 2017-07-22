package com.rizkyfadillah.popularmoviesstage1.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.rizkyfadillah.popularmoviesstage1.BuildConfig;
import com.rizkyfadillah.popularmoviesstage1.MovieDBService;
import com.rizkyfadillah.popularmoviesstage1.api.BaseApiResponse;
import com.rizkyfadillah.popularmoviesstage1.vo.Movie;
import com.rizkyfadillah.popularmoviesstage1.db.MovieContract;
import com.rizkyfadillah.popularmoviesstage1.vo.Review;
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
    private Context context;

    public MovieRepository(MovieDBService service, SQLiteDatabase database, Context context) {
        this.service = service;
        this.database = database;
        this.context = context;
    }

    public Observable<Movie> getMovies(String sort) {
        return service.getMovies(sort)
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
        return service.getMovieVideos(id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(new Function<BaseApiResponse<List<Video>>, Iterable<? extends Video>>() {
                    @Override
                    public Iterable<? extends Video> apply(@NonNull BaseApiResponse<List<Video>> response) throws Exception {
                        return response.results;
                    }
                });
    }

    public Observable<Review> getMovieReviews(String id) {
        return service.getMovieReviews(id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(new Function<BaseApiResponse<List<Review>>, Iterable<? extends Review>>() {
                    @Override
                    public Iterable<? extends Review> apply(@NonNull BaseApiResponse<List<Review>> response) throws Exception {
                        return response.results;
                    }
                });
    }

    public Observable<Long> addFavoriteMovie(final Movie movie) {
        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieContract.MovieEntry._ID, movie.id);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, movie.originalTitle);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER, movie.posterPath);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROP, movie.backdropPath);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, movie.overview);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_COUNT, movie.voteCount);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, movie.voteAverage);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, movie.releaseDate);
                e.onNext(database.insertWithOnConflict(MovieContract.MovieEntry.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE));
                e.onComplete();
            }
        });
    }

    public Observable<Uri> addFavoriteMovie2(final Movie movie) {
        return Observable.create(new ObservableOnSubscribe<Uri>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Uri> e) throws Exception {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieContract.MovieEntry._ID, movie.id);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, movie.originalTitle);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER, movie.posterPath);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROP, movie.backdropPath);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, movie.overview);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_COUNT, movie.voteCount);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, movie.voteAverage);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, movie.releaseDate);
                e.onNext(context.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues));
                e.onComplete();
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
                while (cursor.moveToNext()) {
                    Movie movie = new Movie();
                    movie.backdropPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROP));
                    movie.posterPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER));
                    movie.overview = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW));
                    movie.originalTitle = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE));
                    movie.voteCount = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_COUNT));
                    movie.voteAverage = cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE));
                    movie.releaseDate = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE));
                    e.onNext(movie);
                }
                e.onComplete();
                cursor.close();
            }
        });
    }

    public Observable<Movie> getFavoriteMovies2() {
        final Cursor cursor = context.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        return Observable.create(new ObservableOnSubscribe<Movie>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Movie> e) throws Exception {
                while (cursor.moveToNext()) {
                    Movie movie = new Movie();
                    movie.id = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry._ID));
                    movie.backdropPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROP));
                    movie.posterPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER));
                    movie.overview = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW));
                    movie.originalTitle = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE));
                    movie.voteCount = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_COUNT));
                    movie.voteAverage = cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE));
                    movie.releaseDate = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE));
                    e.onNext(movie);
                }
                e.onComplete();
                cursor.close();
            }
        });
    }
}
