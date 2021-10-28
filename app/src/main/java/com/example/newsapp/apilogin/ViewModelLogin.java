package com.example.newsapp.apilogin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.newsapp.model.UserLogged;

import retrofit2.Call;

public class ViewModelLogin extends AndroidViewModel {
    private RetrofitLogin retrofitLogin;
    private final Call<UserLogged> UserData;
    public ViewModelLogin(@NonNull Application application, String user, String password) {
        super(application);
        retrofitLogin = new RetrofitLogin();
        UserData = retrofitLogin.getAPI().User(user,password);
    }

    public Call<UserLogged> UserDataFunction(){
        return  UserData;
    }
}
