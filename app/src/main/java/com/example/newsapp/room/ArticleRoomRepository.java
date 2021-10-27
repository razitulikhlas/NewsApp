package com.example.newsapp.room;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import com.example.newsapp.login.apilogin.Data;

import java.util.List;

public class ArticleRoomRepository {
    private DaoArticle daoArticle;
    private LiveData<List<ArticleTable>> allArticle;
    private LiveData<ArticleTable> singleArticle;
    private LiveData<List<ArticleTable>> singleArticle1;
    private LiveData<List<ArticleTableBookmark>> allArticleBookmark;
    private LiveData<List<ArticleTableBookmark>> singleArticleBookmark;

    public ArticleRoomRepository(Application application){
        DatabaseArticle database = DatabaseArticle.getDatabase(application);
        daoArticle = database.daoArticle();
        allArticle = daoArticle.getNewsAll();
        allArticleBookmark = daoArticle.getArticleBookmark();
    }

    LiveData<List<ArticleTable>> getAllArticle(){
        return allArticle;
    }

    LiveData<List<ArticleTableBookmark>> getAllArticleBookmark(){
        return allArticleBookmark;
    }

    LiveData<ArticleTable> getSingleArticle(String title){
        singleArticle = daoArticle.getsinglearticle(title);
        return singleArticle;
    }

    LiveData<List<ArticleTable>> getSingleArticle1(String title){
        singleArticle1 = daoArticle.getsinglearticle1(title);
        return singleArticle1;
    }

    LiveData<List<ArticleTableBookmark>> getSingleArticleBookmark(String title){
        singleArticleBookmark = daoArticle.getsinglearticlebookmark(title);
        return singleArticleBookmark;
    }

    void insert(ArticleTable articleTable){
        DatabaseArticle.databaseWriteExecutorArticle.execute(new Runnable() {
            @Override
            public void run() {
                daoArticle.insert(articleTable);
            }
        });
    }

    void insertbookmark(ArticleTableBookmark articleTableBookmark){
        DatabaseArticle.databaseWriteExecutorArticle.execute(new Runnable() {
            @Override
            public void run() {
                daoArticle.insertbookmark(articleTableBookmark);
            }
        });
    }

    void delete(String title){
        DatabaseArticle.databaseWriteExecutorArticle.execute(new Runnable() {
            @Override
            public void run() {
                daoArticle.delete(title);
            }
        });
    }

    void deleteall(){
        DatabaseArticle.databaseWriteExecutorArticle.execute(new Runnable() {
            @Override
            public void run() {
                daoArticle.deleteall();
            }
        });
    }

    void deletearticlebookmark(ArticleTableBookmark articleTableBookmark){
        DatabaseArticle.databaseWriteExecutorArticle.execute(new Runnable() {
            @Override
            public void run() {
                daoArticle.deletearticlebookmark(articleTableBookmark);
            }
        });
    }
}
