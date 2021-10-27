package com.example.newsapp.login.apilogin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import retrofit2.Call;

public class ViewModel extends AndroidViewModel {
    private RetrofitLogin retrofitLogin;
    private final Call<user> UserData;
    public ViewModel(@NonNull Application application, String user, String password) {
        super(application);
        retrofitLogin = new RetrofitLogin();
        UserData = retrofitLogin.getAPI().User(user,password);
    }

    public Call<user> UserDataFunction(){
        return  UserData;
    }
}
