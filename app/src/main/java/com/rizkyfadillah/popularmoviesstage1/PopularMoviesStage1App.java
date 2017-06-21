package com.rizkyfadillah.popularmoviesstage1;

import android.app.Application;

import com.rizkyfadillah.popularmoviesstage1.di.AppComponent;
import com.rizkyfadillah.popularmoviesstage1.di.DaggerAppComponent;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */

public class PopularMoviesStage1App extends Application {

    private AppComponent appComponent = createAppComponent();

    private static PopularMoviesStage1App instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static PopularMoviesStage1App get() {
        return instance;
    }

    protected AppComponent createAppComponent() {
        return appComponent = DaggerAppComponent.builder()
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
