package com.example.newsapp.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.Articles;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {
    private ArticleRepository articleRepository;
    private MutableLiveData<List<Articles>> allArticle = new MutableLiveData<>();

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        articleRepository = new ArticleRepository(application);
        allArticle = articleRepository.getAllArticle();
    }

    public MutableLiveData<List<Articles>> ArticleData() {
        return allArticle;
    }
}
