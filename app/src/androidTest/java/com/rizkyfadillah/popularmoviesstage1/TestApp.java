package com.rizkyfadillah.popularmoviesstage1;

import com.rizkyfadillah.popularmoviesstage1.ui.main.MainActivityComponent;

/**
 * Created by Rizky on 19/02/18.
 */

public class TestApp extends PopularMoviesStage1App {

    private MainActivityComponent mainActivityComponent;

    public void setMainActivityComponent(MainActivityComponent mainActivityComponent) {
        this.mainActivityComponent = mainActivityComponent;
    }

    @Override
    public MainActivityComponent getMainActivityComponent() {
        return mainActivityComponent;
    }

}