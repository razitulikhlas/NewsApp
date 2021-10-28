package com.example.newsapp;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.room.ArticleRoomViewModel;
import com.example.newsapp.sessionmanager.SessionManagerUtil;

import java.util.Calendar;

public class SessionActivity extends AppCompatActivity {

    private ArticleRoomViewModel articleRoomViewModel;

    public void onResume() {
        boolean isAllowed = SessionManagerUtil.getInstance().isSessionActive(this, Calendar.getInstance().getTime());
        System.out.println("isAllowed : " + isAllowed);
        if (!isAllowed) {
            SessionManagerUtil.getInstance().endUserSession(SessionActivity.this);
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            SessionManagerUtil.getInstance().startUserSession(SessionActivity.this, 300);
        }
        super.onResume();
    }
}