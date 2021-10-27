package com.example.newsapp.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ArticleRoomViewModel extends AndroidViewModel {
    private ArticleRoomRepository articleRoomRepository;
    private final LiveData<List<ArticleTable>> allArticle;
    private final LiveData<List<ArticleTableBookmark>> allArticleBookmark;

    public ArticleRoomViewModel(@NonNull Application application) {
        super(application);
        articleRoomRepository = new ArticleRoomRepository(application);
        allArticle = articleRoomRepository.getAllArticle();
        allArticleBookmark = articleRoomRepository.getAllArticleBookmark();
    }

    public LiveData<List<ArticleTable>> getAllArticle(){
        return allArticle;
    }

    public LiveData<List<ArticleTableBookmark>> getAllArticleBookmark(){
        return allArticleBookmark;
    }

    public LiveData<ArticleTable> getSingleArticle(String title){
        return articleRoomRepository.getSingleArticle(title);
    }

    public LiveData<List<ArticleTable>> getSingleArticle1(String title){
        return articleRoomRepository.getSingleArticle1(title);
    }

    public LiveData<List<ArticleTableBookmark>> getsinglearticlebookmark(String title){
        return articleRoomRepository.getSingleArticleBookmark(title);
    }

    public void insert(ArticleTable articleTable){
        articleRoomRepository.insert(articleTable);
    }

    public void insertseelater(ArticleTableBookmark articleTableBookmark){
        articleRoomRepository.insertbookmark(articleTableBookmark);
    }
    public void delete(String title){
        articleRoomRepository.delete(title);
    }

    public void deleteall(){
        articleRoomRepository.deleteall();
    }

    public void deletearticlebookmark(ArticleTableBookmark articleTableBookmark){
        articleRoomRepository.deletearticlebookmark(articleTableBookmark);
    }
}
