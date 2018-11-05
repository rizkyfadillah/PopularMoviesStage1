package com.rizkyfadillah.popularmoviesstage1;

import android.app.Application;

import com.rizkyfadillah.popularmoviesstage1.di.AppComponent;
import com.rizkyfadillah.popularmoviesstage1.di.AppModule;
import com.rizkyfadillah.popularmoviesstage1.di.DaggerAppComponent;
import com.rizkyfadillah.popularmoviesstage1.ui.main.MainActivityComponent;
import com.rizkyfadillah.popularmoviesstage1.ui.main.MainActivityModule;

import timber.log.Timber;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */

public class PopularMoviesStage1App extends Application {

    private AppComponent appComponent;

    private static PopularMoviesStage1App instance;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        instance = this;
    }

    public static PopularMoviesStage1App get() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public MainActivityComponent getMainActivityComponent() {
        return appComponent.plus(new MainActivityModule());
    }

}
