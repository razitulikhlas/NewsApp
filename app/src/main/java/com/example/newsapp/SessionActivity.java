package com.example.newsapp;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.newsapp.login.apilogin.SessionManager;
import com.example.newsapp.room.ArticleRoomViewModel;

import java.util.Calendar;

public class SessionActivity extends AppCompatActivity {

    private ArticleRoomViewModel articleRoomViewModel;

    public void onResume() {
        boolean isAllowed = SessionManager.getInstance().isSessionActive(this, Calendar.getInstance().getTime());
        if (!isAllowed) {
            articleRoomViewModel = new ViewModelProvider(SessionActivity.this).get(ArticleRoomViewModel.class);
            articleRoomViewModel.deleteall();
            SessionManager.getInstance().endUserSession(SessionActivity.this);
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else { // edit disini agar kalau ada aktifitas sessionnya tidak expired tetapi bertambah.
            SessionManager.getInstance().startUserSession(SessionActivity.this, 300);
        }
        super.onResume();
    }

}