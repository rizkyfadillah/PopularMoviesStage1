package com.rizkyfadillah.popularmoviesstage1;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Rizky Fadillah on 22/07/2017.
 */

public class ServiceInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        HttpUrl url = request.url().newBuilder()
                .addQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_TOKEN)
                .build();

        request = request.newBuilder()
                .url(url)
                .build();

        return chain.proceed(request);
    }
}
