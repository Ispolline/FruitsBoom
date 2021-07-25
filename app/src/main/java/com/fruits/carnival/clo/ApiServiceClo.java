package com.fruits.carnival.clo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServiceClo {

    @GET("http://cs37267.tmweb.ru/content/")
    Call<String> getResponse();
}
