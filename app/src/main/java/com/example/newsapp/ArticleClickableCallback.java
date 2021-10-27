package com.example.newsapp;

import android.view.View;

import com.example.newsapp.room.ArticleTable;

public interface ArticleClickableCallback {
    void onClick(View view, ArticleTable articleTable);
}
