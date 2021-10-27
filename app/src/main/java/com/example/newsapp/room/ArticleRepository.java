package com.example.newsapp.room;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.Articles;
import com.example.newsapp.RetrofitAPI;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArticleRepository {
    private RetrofitAPI API;
    private MutableLiveData<List<Articles>> allArticle = new MutableLiveData<>();

    private final ExecutorService networkExecutor =
            Executors.newFixedThreadPool(4);
    private final Executor mainThread = new Executor() {
        private Handler handler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    };

    public ArticleRepository(Application application){
        RetrofitArticle retrofitArticle = new RetrofitArticle();
        API = retrofitArticle.getAPI();
    }

    public void getArticleData(){
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Articles> ArticleDataResp = API.ArticleData().execute().body();
                    mainThread.execute(new Runnable() {
                        @Override
                        public void run() {
                            allArticle.setValue(ArticleDataResp);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MutableLiveData<List<Articles>> getAllArticle(){
        if (allArticle.getValue() == null || allArticle.getValue().isEmpty())
            getArticleData();
        return allArticle;
    }
}
