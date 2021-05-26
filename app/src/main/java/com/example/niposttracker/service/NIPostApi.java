package com.example.niposttracker.service;

import com.example.niposttracker.model.LoginResponse;
import com.example.niposttracker.model.UserResponse;
import com.example.niposttracker.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NIPostApi {


    @POST("api/user/login")
    public Call<LoginResponse> getLoginResponse(@Query("email") String email, @Query("password") String password);

//    @Headers("Content-Type: application/json")
    @GET("api/user")
    public Call<LoginResponse> getUserResponse(@Header("Authorization") String token);

}
