package com.fruits.carnival.system;

import retrofit.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class ApiClientSmsGorod {

    private static ApiClientSmsGorod mInstance;
    private static final String BASE_URL = "https://new.smsgorod.ru/apiSms/";
    private final Retrofit mRetrofit;

    private ApiClientSmsGorod(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static ApiClientSmsGorod getInstance(){
        if(mInstance == null) {
            mInstance = new ApiClientSmsGorod();
        }
        return mInstance;
    }

    public ApiServiceSmsGorod getApiServiceSmsGorod(){
        return mRetrofit.create(ApiServiceSmsGorod.class);
    }


}