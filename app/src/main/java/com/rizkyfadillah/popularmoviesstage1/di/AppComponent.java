package com.rizkyfadillah.popularmoviesstage1.di;

import android.provider.ContactsContract;

import com.rizkyfadillah.popularmoviesstage1.ui.detail.DetailActivityComponent;
import com.rizkyfadillah.popularmoviesstage1.ui.detail.DetailActivityModule;
import com.rizkyfadillah.popularmoviesstage1.ui.main.MainActivityComponent;
import com.rizkyfadillah.popularmoviesstage1.ui.main.MainActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */
@Singleton
@Component(
        modules = {
                AppModule.class,
                MovieDBApiModule.class,
                DataModule.class
        })
public interface AppComponent {

    MainActivityComponent plus(MainActivityModule mainActivityModule);

    DetailActivityComponent plus(DetailActivityModule detailActivityModule);

}
