package com.example.app.bean;

import java.io.Serializable;

public class Posts implements Serializable {
    private String postUsername;
    private String postTime;
    private String postContent;

    public void setPostUsername(String postUsername){
        this.postUsername = postUsername;
    }
    public String getPostUsername(){ return postUsername; }

    public void setPostTime(String postTime){
        this.postTime = postTime;
    }
    public String getPostTime(){ return postTime; }

    public void setPostContent(String postContent){
        this.postContent = postContent;
    }
    public String getPostContent(){ return postContent; }
}
