package com.rizkyfadillah.popularmoviesstage1.ui.detail;

import com.rizkyfadillah.popularmoviesstage1.di.ActivityScope;
import com.rizkyfadillah.popularmoviesstage1.repository.MovieRepository;

import dagger.Module;
import dagger.Provides;

/**
 * @author Rizky Fadillah on 17/07/2017.
 */
@Module
public class DetailActivityModule {

    @Provides
    @ActivityScope
    public DetailViewModel provideDetailViewModel(MovieRepository movieRepository) {
        return new DetailViewModel(movieRepository);
    }

}
