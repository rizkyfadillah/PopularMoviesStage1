package com.rizkyfadillah.popularmoviesstage1.ui.main;

import com.rizkyfadillah.popularmoviesstage1.MovieDBService;
import com.rizkyfadillah.popularmoviesstage1.di.ActivityScope;
import com.rizkyfadillah.popularmoviesstage1.repository.MovieRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */
@Module
public class MainActivityModule {

//    @Provides
//    @ActivityScope
//    MovieRepository provideMovieRepository(MovieDBService movieDBService) {
//        return new MovieRepository(movieDBService);
//    }

    @Provides
    @ActivityScope
    MainViewModel provideMainViewModel(MovieRepository movieRepository) {
        return new MainViewModel(movieRepository);
    }

}
