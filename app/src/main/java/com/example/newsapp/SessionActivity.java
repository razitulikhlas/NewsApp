package com.example.newsapp;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.newsapp.room.ArticleRoomViewModel;
import com.example.newsapp.sessionmanager.SessionManagerUtil;

import java.util.Calendar;

public class SessionActivity extends AppCompatActivity {

    private ArticleRoomViewModel articleRoomViewModel;

    public void onResume() {
        boolean isAllowed = SessionManagerUtil.getInstance().isSessionActive(this, Calendar.getInstance().getTime());
        System.out.println("isAllowed : " + isAllowed);
        Log.e("TAG", "onResume sessiom: "+isAllowed );
        if (!isAllowed) {
            Log.e("TAG", "onResume: 12" );
            articleRoomViewModel = new ViewModelProvider(SessionActivity.this).get(ArticleRoomViewModel.class);
            articleRoomViewModel.deleteall();
            SessionManagerUtil.getInstance().endUserSession(SessionActivity.this);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        super.onResume();
    }



}