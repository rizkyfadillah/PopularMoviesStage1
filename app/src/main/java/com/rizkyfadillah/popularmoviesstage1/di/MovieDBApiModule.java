package com.rizkyfadillah.popularmoviesstage1.di;

import com.rizkyfadillah.popularmoviesstage1.MovieDBService;
import com.rizkyfadillah.popularmoviesstage1.repository.MovieRepository;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rizky Fadillah on 15/06/2017.
 * Android Developer
 */
@Module
public class MovieDBApiModule {

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRestAdapter(OkHttpClient okHttpClient) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(okHttpClient)
                .baseUrl("https://api.themoviedb.org")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        return builder.build();
    }

    @Provides
    @Singleton
    MovieDBService provideMovieDBService(Retrofit restAdapter) {
        return restAdapter.create(MovieDBService.class);
    }

    @Provides
    @Singleton
    MovieRepository provideMovieRepository(MovieDBService movieDBService) {
        return new MovieRepository(movieDBService);
    }

}
