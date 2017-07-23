package com.rizkyfadillah.popularmoviesstage1.di;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rizkyfadillah.popularmoviesstage1.MovieDBService;
import com.rizkyfadillah.popularmoviesstage1.repository.MovieRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Rizky Fadillah on 17/07/2017.
 */
@Module(includes = {MovieDBApiModule.class, DbModule.class})
class DataModule {

    @Provides
    @Singleton
    MovieRepository provideMovieRepository(MovieDBService movieDBService, SQLiteDatabase database, Context context) {
        return new MovieRepository(movieDBService, database, context);
    }

}
