package com.rizkyfadillah.popularmoviesstage1.di;

import com.rizkyfadillah.popularmoviesstage1.ui.detail.DetailMovieActivityComponent;
import com.rizkyfadillah.popularmoviesstage1.ui.detail.DetailMovieActivityModule;
import com.rizkyfadillah.popularmoviesstage1.ui.main.MainActivityComponent;
import com.rizkyfadillah.popularmoviesstage1.ui.main.MainActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */
@Singleton
@Component(
        modules = {
                AppModule.class,
                MovieDBApiModule.class
        })
public interface AppComponent {

    MainActivityComponent plus(MainActivityModule mainActivityModule);

    DetailMovieActivityComponent plus(DetailMovieActivityModule detailMovieActivityModule);

}
