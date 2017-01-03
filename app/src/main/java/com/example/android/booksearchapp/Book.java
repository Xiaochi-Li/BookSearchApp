package com.example.android.booksearchapp;

/**
 * Created by lixiaochi on 27/12/16.
 */

public class Book {

    private String title;
    private String author;
    private String description;

    public Book(String title, String author, String description){
        this.title=title;
        this.author=author;
        this.description=description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }
}
