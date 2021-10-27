package com.example.newsapp.room;

import com.example.newsapp.RetrofitAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitArticle {
    private RetrofitAPI APINews;

    public RetrofitArticle(){
        String BASE_URL = "https://newsapi.org/";

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofit.create(RetrofitAPI.class);
        APINews = retrofit.create(RetrofitAPI.class);
    }

    public RetrofitAPI getAPI() {
        return APINews;
    }
}
