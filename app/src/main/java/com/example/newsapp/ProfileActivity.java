package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends LoginActivity {
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
        this.startActivity(intent);
        finish();
    }

    public void onBackPressed(){
        //super.onBackPressed();
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        this.startActivity(intent);
        finish();
    }
}
