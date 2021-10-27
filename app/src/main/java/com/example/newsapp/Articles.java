package com.example.newsapp;

public class Articles {
	private String title;
	private String author;
	private String description;
	private String urlToImage;
	private String url;
	private String content;
	private String publishedAt;

	public Articles(String title,String author, String description, String urlToImage, String url, String content, String publishedAt) {
		this.title = title;
		this.author = author;
		this.description = description;
		this.urlToImage = urlToImage;
		this.url = url;
		this.content = content;
		this.publishedAt = publishedAt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrlToImage() {
		return urlToImage;
	}

	public void setUrlToImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}
}