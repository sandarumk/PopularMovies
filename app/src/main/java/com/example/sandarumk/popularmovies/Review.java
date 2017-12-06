package com.example.sandarumk.popularmovies;

/**
 * Created by dinu on 12/3/17.
 */

public class Review {

    private String id;
    private String content;
    private String author;

    public Review(String id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}
