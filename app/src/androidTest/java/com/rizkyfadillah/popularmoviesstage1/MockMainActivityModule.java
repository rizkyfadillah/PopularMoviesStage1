package com.rizkyfadillah.popularmoviesstage1;

import com.rizkyfadillah.popularmoviesstage1.di.ActivityScope;
import com.rizkyfadillah.popularmoviesstage1.ui.main.MainViewModel;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * Created by Rizky on 19/02/18.
 */
@Module
public class MockMainActivityModule {

    @Provides
    @ActivityScope
    static MainViewModel provideMainViewModel() {
        return mock(MainViewModel.class);
    }

}