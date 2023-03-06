package com.example.totcoapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface RetrofitAPI{


    @POST("/users")
    @Headers("Content-Type: application/json")
//    @FormUrlEncoded
    Call<DataModal> createPost(@Body DataModal dataModal);

//
//    Call<DataModal> savePost(@Field("name")String name,
//                               @Field("password") String password);

}
