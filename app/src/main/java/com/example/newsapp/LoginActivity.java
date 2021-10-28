package com.example.newsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.model.Data;
import com.example.newsapp.model.ErrorLog;
import com.example.newsapp.apilogin.InterfaceApiLogin;
import com.example.newsapp.apilogin.ViewModelLogin;
import com.example.newsapp.model.UserLogged;
import com.example.newsapp.sessionmanager.SessionManagerUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    private ProgressBar pb;
    static String User = "", pass = "", token = "";
    private InterfaceApiLogin API;

    private Executor backgroundThread = Executors.newSingleThreadExecutor();
    private Executor mainThread = new Executor() {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        pb = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void validate(View view){
        User = username.getText().toString().toLowerCase();
        pass = password.getText().toString().toLowerCase();
        if(User.equalsIgnoreCase("") && !pass.equalsIgnoreCase("")){
            Toast.makeText(LoginActivity.this,"Username is empty", Toast.LENGTH_SHORT).show();
        }
        else if(!User.equalsIgnoreCase("") && pass.equalsIgnoreCase("")){
            Toast.makeText(LoginActivity.this,"Password is empty", Toast.LENGTH_SHORT).show();
        }
        else if(!User.equalsIgnoreCase("") && !pass.equalsIgnoreCase("")){
            loginapi();
        }
        else{
            Toast.makeText(LoginActivity.this,"Username & password is empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void loginapi(){
        pb.setVisibility(View.VISIBLE);
        backgroundThread.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    mainThread.execute(new Runnable() {
                        @Override
                        public void run() {
                            hitloginapi();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String generateToken(String tokenuser){
        String token = "";
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){ // jika dibawah sdk poitn 0 akan crash kalau pakai base64
            token = Base64.getEncoder().encodeToString(tokenuser.getBytes());
        }
        else{
            token = tokenuser;
        }

        return token;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void hitloginapi(){
        Call<UserLogged> resultlogin = new ViewModelLogin(this.getApplication(), User, pass).UserDataFunction();//viewModel.UserDataFunction();
        resultlogin.enqueue(new Callback<UserLogged>() {
            @Override
            public void onResponse(Call<UserLogged> call, Response<UserLogged> response) {
                pb.setVisibility(View.INVISIBLE);
                if(response.code()==200){
                    UserLogged responselogin = response.body();
                    Data datauser = responselogin.getData();
                    SessionManagerUtil.getInstance().storeUserToken(LoginActivity.this,datauser.getUsername());
                    token = responselogin.getToken();
                    String fullname = datauser.getFullName();
                    String email = datauser.getEmail();
                    System.out.println("fullname : "+fullname+"\nemail : "+email);
                    startandstoresession();
                    startLoginActivity(fullname, email);
                }
                else{
                    Gson gson = new Gson();
                    ErrorLog responseerror = null;
                    try {
                        responseerror = gson.fromJson(response.errorBody().string(), ErrorLog.class);
                        errorlogin(responseerror.getMessage().toUpperCase());
                    } catch (IOException e) {
                        errorlogin("Something Wrong Please Try Again Later");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserLogged> call, Throwable t) {
                errorlogin("Something Wrong Please Try Again Later");
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startandstoresession(){
        SessionManagerUtil.getInstance().storeUserToken(LoginActivity.this, generateToken(token));
        SessionManagerUtil.getInstance().startUserSession(LoginActivity.this, 300);
    }

    public void startLoginActivity(String name, String email){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("name_key",name);
        intent.putExtra("email_key",email);
        this.startActivity(intent);
        finish();
    }

    public void errorlogin(String message){
        AlertDialog.Builder dialog2 = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialog_AppCompat);
        dialog2.setTitle("ERROR");
        dialog2.setMessage(message).setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog3 = dialog2.create();
        dialog3.show();
        Button negativeButton = dialog3.getButton(AlertDialog.BUTTON_NEGATIVE);
//        negativeButton.setTextColor(getResources().getColor(R.color.red));
    }
}