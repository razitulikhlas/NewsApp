package com.example.newsapp.apilogin;

import static com.example.newsapp.apilogin.InterfaceApiLogin.LOGIN_URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitLogin {
    private InterfaceApiLogin API;

    public RetrofitLogin(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LOGIN_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofit.create(InterfaceApiLogin.class);
        API = retrofit.create(InterfaceApiLogin.class);
    }

    public InterfaceApiLogin getAPI() {
        return API;
    }
}
