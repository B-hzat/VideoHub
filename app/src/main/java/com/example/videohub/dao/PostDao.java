package com.example.videohub.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.videohub.Entities.Post;

import java.util.List;
@Dao
public interface PostDao {
    @Insert
    void InsertPost(Post post);

    @Insert
    void InsertPosts(Post... posts);

    @Delete
    void DeletePost(Post post);

    @Delete
    void DeletePosts(Post... posts);

    @Update
    void UpdatePost(Post post);

    @Query("SELECT * FROM tblPost")
    LiveData<List<Post>> getAllPosts();

    @Query("SELECT * FROM tblPost WHERE postId = :Id")
    LiveData<List<Post>> FindPostById(int Id);

}
