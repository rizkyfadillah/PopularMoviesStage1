package com.rizkyfadillah.popularmoviesstage1.di;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rizkyfadillah.popularmoviesstage1.db.MovieDbHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Rizky Fadillah on 17/07/2017.
 */
@Module
public class DataModule {

    private final Class mDbHelperClass = MovieDbHelper.class;

//    @Provides
//    @Singleton
//    public SQLiteOpenHelper provideSQLiteOpenHelper(Context context) {
//        try {
//            return (SQLiteOpenHelper) mDbHelperClass.getConstructor(Context.class).newInstance(context);
//        } catch (Exception e) {
//            return null;
//        }
//    }

    @Provides
    @Singleton
    public MovieDbHelper provideMovieDbHelper(Context context) {
        return new MovieDbHelper(context);
    }

    @Provides
    @Singleton
    public SQLiteDatabase provideSQLiteDatabase(MovieDbHelper movieDbHelper) {
        return movieDbHelper.getWritableDatabase();
    }

//    @Provides
//    @Singleton
//    public SQLiteDatabase provideSQLiteDatabase(SQLiteOpenHelper dbHelper) {
//        return dbHelper.getWritableDatabase();
//    }

}
