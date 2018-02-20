package com.rizkyfadillah.popularmoviesstage1.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.rizkyfadillah.popularmoviesstage1.MovieDBService;
import com.rizkyfadillah.popularmoviesstage1.api.BaseApiResponse;
import com.rizkyfadillah.popularmoviesstage1.db.MovieContract;
import com.rizkyfadillah.popularmoviesstage1.vo.Movie;
import com.rizkyfadillah.popularmoviesstage1.vo.Review;
import com.rizkyfadillah.popularmoviesstage1.vo.Video;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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

    private final MovieDBService service;
    private final Context context;

    @Inject
    public MovieRepository(MovieDBService service, Context context) {
        this.service = service;
        this.context = context;
    }

    public Observable<List<Movie>> getMovies(String sort) {
        if (sort.equals("favorite")) {
            return getFavoriteMovies();
        } else {
            return service.getMovies(sort)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Function<BaseApiResponse<List<Movie>>, List<Movie>>() {
                        @Override
                        public List<Movie> apply(BaseApiResponse<List<Movie>> listBaseApiResponse) throws Exception {
                            return listBaseApiResponse.results;
                        }
                    });
        }
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

    public Observable<Uri> addFavoriteMovie(final Movie movie) {
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

    private Observable<List<Movie>> getFavoriteMovies() {
        final Cursor cursor = context.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        return Observable.create(new ObservableOnSubscribe<List<Movie>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Movie>> e) throws Exception {
                if (cursor != null) {
                    List<Movie> movies = new ArrayList<>();
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
                        movies.add(movie);
                    }
                    e.onNext(movies);
                    cursor.close();
                    e.onComplete();
                }
            }
        });
    }

    public Movie findMovieById(String id) {
        String [] whereArgs = {id};

        Cursor cursor = context.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                null,
                MovieContract.MovieEntry._ID + " = ?",
                whereArgs,
                null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                Movie movie = new Movie();
                movie.id = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry._ID));
                movie.backdropPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROP));
                movie.posterPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER));
                movie.overview = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW));
                movie.originalTitle = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE));
                movie.voteCount = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_COUNT));
                movie.voteAverage = cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE));
                movie.releaseDate = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE));
                return movie;
            }
            cursor.close();
        }

        return null;
    }

    public int deleteMovieById(String id) {
        Uri uriToDelete = MovieContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(id).build();

        return context.getContentResolver().delete(uriToDelete,
                null,
                null);
    }

}
