package com.example.newsapp.room;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM article")
    LiveData<List<Article>> getAll();

    @Query("SELECT * FROM article WHERE uid IN (:articleIds)")
    LiveData<List<Article>> loadAllByIds(int[] articleIds);

    @Query("SELECT * FROM article WHERE title LIKE :first AND " +
            "description LIKE :last LIMIT 1")
    LiveData<Article> findByName(String first, String last);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

    @Update
    void update(Article article);

    @Delete
    void delete(Article article);
}