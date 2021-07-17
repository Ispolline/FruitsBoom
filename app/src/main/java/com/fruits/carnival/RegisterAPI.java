package com.fruits.carnival;

import com.squareup.okhttp.Response;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("/RetrofitExample/insert.php")
    public void insertUser(
            @Field("name") String name,
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email,
            Callback<Response> callback);
}