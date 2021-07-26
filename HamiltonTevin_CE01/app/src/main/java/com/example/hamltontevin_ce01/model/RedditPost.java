package com.example.hamltontevin_ce01.model;

import java.io.Serializable;

public class RedditPost implements Serializable {

    private final String mTitle;
    private final String mLink;
    private final int mNumComments ;

    public RedditPost(String mTitle, String mLink, int mNumComments) {
        this.mTitle = mTitle;
        this.mLink = mLink;
        this.mNumComments = mNumComments;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getLink() {
        return mLink;
    }

    public int getNumComments() {
        return mNumComments;
    }
}
