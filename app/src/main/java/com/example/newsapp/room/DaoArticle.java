package com.example.newsapp.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoArticle {
    @Query("SELECT * FROM articleTable")
    LiveData<List<ArticleTable>> getNewsAll();

    @Query("SELECT * FROM articleTable WHERE title = :title")
    LiveData<ArticleTable> getsinglearticle(String title);

    @Query("SELECT * FROM articleTable WHERE upper(title) = :title")
    LiveData<List<ArticleTable>> getsinglearticle1(String title);

    @Query("SELECT * FROM articletablebookmark")
    LiveData<List<ArticleTableBookmark>> getArticleBookmark();

    @Query("SELECT * FROM articletablebookmark WHERE upper(title) = :title")
    LiveData<List<ArticleTableBookmark>> getsinglearticlebookmark(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArticleTable articleTable);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertbookmark(ArticleTableBookmark articleTableBookmark);

    @Query("DELETE FROM articletablebookmark WHERE title = :title")
    void delete(String title);

    @Query("DELETE FROM articleTable")
    void deleteall();

    @Delete
    void deletearticlebookmark(ArticleTableBookmark articleTableBookmark);
}
