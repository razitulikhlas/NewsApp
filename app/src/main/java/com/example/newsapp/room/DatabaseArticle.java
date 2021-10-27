package com.example.newsapp.room;


import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {ArticleTable.class,ArticleTableBookmark.class}, version = 1, exportSchema = false)
public abstract class DatabaseArticle extends RoomDatabase {
    public abstract DaoArticle daoArticle();

    private static volatile DatabaseArticle INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutorArticle =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static DatabaseArticle getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseArticle.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DatabaseArticle.class, "article_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
