package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.newsapp.room.ArticleRoomViewModel;
import com.example.newsapp.sessionmanager.SessionManagerUtil;

import java.util.Calendar;

public class ProfileActivity extends SessionActivity {
    private String fullname;
    private String email;
    private TextView fullname_view;
    private TextView email_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();

        fullname = intent.getStringExtra("fullname");
        email = intent.getStringExtra("email");

        fullname_view = (TextView) findViewById(R.id.profile_name);
        email_view = (TextView) findViewById(R.id.profile_email);

        fullname_view.setText(fullname);
        email_view.setText(email);
    }

    public void backToHome(View view){
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("fullname",fullname);
        intent.putExtra("email",email);
        this.startActivity(intent);
        finish();
    }

    public void onBackPressed(){
        super.onBackPressed();
    }

}
