package com.example.videohub.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.videohub.Entities.Post;
import com.example.videohub.Repository.PostRepository;
import com.example.videohub.roomDB.UserRoomDB;

import java.util.List;

public class PostViewModel extends AndroidViewModel {

    private PostRepository postRepository;
    private LiveData<List<Post>> liveDataPost;

    public PostViewModel(@NonNull Application application) {
        super(application);
        postRepository = new PostRepository(application);
        liveDataPost= postRepository.getAllPosts();

    }

    public LiveData<List<Post>> getAllPosts()
    {
        return liveDataPost;
    }

    public LiveData<List<Post>> FindPostById(int Id)
    {
        return postRepository.FindPostById(Id);
    }

    public void InsertPost(Post post)
    {
            postRepository.InsertPost(post);
    }

    public void InsertPosts(Post... posts)
    {
            postRepository.InsertPosts(posts);
    }

    public void DeletePost(Post post)
    {
            postRepository.DeletePost(post);
    }

    public void DeletePosts(Post... posts)
    {
            postRepository.DeletePosts(posts);
    }

    public void UpdatePost(Post post)
    {
            postRepository.UpdatePost(post);
    }


}
