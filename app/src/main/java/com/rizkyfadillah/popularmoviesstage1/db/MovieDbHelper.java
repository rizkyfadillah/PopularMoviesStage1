package com.rizkyfadillah.popularmoviesstage1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Rizky Fadillah on 17/07/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie.db";

    private static final int DATABASE_VERSION = 7;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE =
                "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                        MovieContract.MovieEntry._ID                        + " TEXT PRIMARY KEY, " +
                        MovieContract.MovieEntry.COLUMN_MOVIE_TITLE         + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.COLUMN_MOVIE_POSTER        + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROP      + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW      + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE  + " DOUBLE NOT NULL, " +
                        MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_COUNT    + " INT NOT NULL, " +
                        MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL" +
                "); ";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }

}
