package com.rizkyfadillah.popularmoviesstage1.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */
@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return application;
    }

}
