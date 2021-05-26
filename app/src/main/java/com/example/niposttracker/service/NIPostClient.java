package com.example.niposttracker.service;

import com.example.niposttracker.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NIPostClient {

    private static Retrofit INSTANCE;
    private static final

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getInstance(){
        if (INSTANCE == null){
            INSTANCE=new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(new OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return INSTANCE;
    }

}
