package com.fruits.carnival.clo;

import android.content.Context;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;


import java.io.IOException;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientClo  {

    private static ApiClientClo mInstance;
    private static final String BASE_URL = "http://cs37267.tmweb.ru/content/";
    private final Retrofit mRetrofit;
    private final Context mContext;

    private static class UserAgentInterceptor implements Interceptor {

        private final String userAgent;

        public UserAgentInterceptor(String userAgent) {
            this.userAgent = userAgent;
        }


        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .header("User-Agent", userAgent)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }

    }

    private ApiClientClo(Context context){

        mContext = context.getApplicationContext();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        OkHttpClient client = httpClient.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static ApiClientClo getInstance(Context context){
        if(mInstance == null){
            mInstance = new ApiClientClo(context);
        }
        return mInstance;
    }

    public ApiServiceClo getApiServiceMagicChecker(){
        return mRetrofit.create(ApiServiceClo.class);
    }
}