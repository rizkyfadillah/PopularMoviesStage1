package com.rizkyfadillah.popularmoviesstage1.di;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rizkyfadillah.popularmoviesstage1.MovieDBService;
import com.rizkyfadillah.popularmoviesstage1.Utils;
import com.rizkyfadillah.popularmoviesstage1.repository.MovieRepository;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    OkHttpClient provideOkhttpClient(final Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //setup cache
//        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
//        int cacheSize = 10 * 1024 * 1024; // 10 MiB
//        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Response originalResponse = chain.proceed(chain.request());
//                        if (Utils.isNetworkConnected(context)) {
//                            int maxAge = 60; // read from cache for 1 minute
//                            return originalResponse.newBuilder()
//                                    .header("Cache-Control", "public, max-age=" + maxAge)
//                                    .build();
//                        } else {
//                            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
//                            return originalResponse.newBuilder()
//                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                                    .build();
//                        }
//                    }
//                })
//                .cache(cache)
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
    MovieRepository provideMovieRepository(MovieDBService movieDBService, SQLiteDatabase database) {
        return new MovieRepository(movieDBService, database);
    }

}
