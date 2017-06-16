package com.rizkyfadillah.popularmoviesstage1.ui.detail;

import com.rizkyfadillah.popularmoviesstage1.di.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */
@ActivityScope
@Subcomponent(
        modules = DetailMovieActivityModule.class
)
public interface DetailMovieActivityComponent {

    void inject(DetailMovieActivity detailMovieActivity);

}
