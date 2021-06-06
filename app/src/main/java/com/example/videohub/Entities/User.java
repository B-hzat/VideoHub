package com.example.videohub.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tblUser")
public class User {


    @PrimaryKey(autoGenerate = true)
    @NonNull
    int userID;
    @NonNull
    String username;
    @NonNull
    String email;
    @NonNull
    String password;
    @NonNull
    String ImageUrl;

    @NonNull
    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(@NonNull String imageUrl) {
        ImageUrl = imageUrl;
    }



    public User(int userID, String username, String email, String password) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public User() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
