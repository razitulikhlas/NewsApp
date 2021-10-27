package com.example.newsapp.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Article {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "urlToImage")
    public String urlToImage;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "publishedAt")
    public String publishedAt;
}
