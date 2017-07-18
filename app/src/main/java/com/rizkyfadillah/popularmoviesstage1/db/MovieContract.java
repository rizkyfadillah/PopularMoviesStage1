package com.rizkyfadillah.popularmoviesstage1.db;

import android.provider.BaseColumns;

/**
 * @author Rizky Fadillah on 17/07/2017.
 */

public class MovieContract {

    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_MOVIE_POSTER = "movie_poster";
    }

}
