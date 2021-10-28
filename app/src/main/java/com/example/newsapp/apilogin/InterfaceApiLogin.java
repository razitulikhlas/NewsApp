package com.example.newsapp.apilogin;

import com.example.newsapp.model.UserLogged;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface InterfaceApiLogin {
    String LOGIN_URL = "https://talentpool.oneindonesia.id/api/user/";

    @Headers("X-API-KEY:454041184B0240FBA3AACD15A1F7A8BB")
    @FormUrlEncoded
    @POST("login")
    Call<UserLogged> User(@Field("username") String user, @Field("password") String pass);
}
