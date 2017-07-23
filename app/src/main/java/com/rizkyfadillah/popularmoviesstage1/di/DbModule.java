package com.rizkyfadillah.popularmoviesstage1.di;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rizkyfadillah.popularmoviesstage1.db.MovieDbHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Rizky Fadillah on 18/07/2017.
 */
@Module
class DbModule {

    @Provides
    @Singleton
    MovieDbHelper provideMovieDbHelper(Context context) {
        return new MovieDbHelper(context);
    }

    @Provides
    @Singleton
    SQLiteDatabase provideSQLiteDatabase(MovieDbHelper movieDbHelper) {
        return movieDbHelper.getWritableDatabase();
    }

}
