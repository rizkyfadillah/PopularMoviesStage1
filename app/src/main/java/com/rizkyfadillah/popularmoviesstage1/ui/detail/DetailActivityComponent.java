package com.rizkyfadillah.popularmoviesstage1.ui.detail;

import com.rizkyfadillah.popularmoviesstage1.di.ActivityScope;

import dagger.Component;
import dagger.Subcomponent;

/**
 * @author Rizky Fadillah on 17/07/2017.
 */
@ActivityScope
@Subcomponent(modules = DetailActivityModule.class)
public interface DetailActivityComponent {

    void inject(DetailMovieActivity detailMovieActivity);

}
