package com.example.videohub.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "tblPost")
public class Post {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int postId;
    @NonNull
    private int likeCount;
    @NonNull
    private int profileIcon;
    @NonNull
    private int postImage;
    @NonNull
    private String title;
    private String message;


    public int getLikeCount() {return likeCount; }

    public void setLikeCount(int likeCount) {this.likeCount = likeCount;}

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(int profileIcon) {
        this.profileIcon = profileIcon;
    }

    public int getPostImage() {
        return postImage;
    }

    public void setPostImage(int postImage) {
        this.postImage = postImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Post(int postId, int profileIcon, int postImage, String title, String message) {
        this.postId = postId;
        this.profileIcon = profileIcon;
        this.postImage = postImage;
        this.title = title;
        this.message = message;
        this.likeCount=0;
    }

}
