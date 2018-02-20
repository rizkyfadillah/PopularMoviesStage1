package com.rizkyfadillah.popularmoviesstage1.ui.main;

import com.rizkyfadillah.popularmoviesstage1.di.ActivityScope;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */
@ActivityScope
@Subcomponent(
        modules = MainActivityModule.class
)
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);

}
