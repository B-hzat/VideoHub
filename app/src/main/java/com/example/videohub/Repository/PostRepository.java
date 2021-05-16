package com.example.videohub.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.videohub.Entities.Post;
import com.example.videohub.Entities.User;
import com.example.videohub.dao.PostDao;
import com.example.videohub.dao.UserDao;
import com.example.videohub.roomDB.UserRoomDB;

import java.util.List;

public class PostRepository {
    private PostDao postDao;
    private LiveData<List<Post>> liveDataPost;

    public PostRepository(Application application)
    {
        UserRoomDB db= UserRoomDB.getDatabase(application.getApplicationContext());
        postDao=db.getPostDao();
        liveDataPost=postDao.getAllPosts();
    }

    public LiveData<List<Post>> getAllPosts()
    {
        return liveDataPost;
    }

    public LiveData<List<Post>> FindPostById(int Id)
    {
        return postDao.FindPostById(Id);
    }

    public void InsertPost(Post post)
    {
        UserRoomDB.databaseWriteExecutor.execute(()->{
            postDao.InsertPost(post);
        });
    }

    public void InsertPosts(Post... posts)
    {
        UserRoomDB.databaseWriteExecutor.execute(()->{
            postDao.InsertPosts(posts);
        });
    }

    public void DeletePost(Post post)
    {
        UserRoomDB.databaseWriteExecutor.execute(()->{
            postDao.DeletePost(post);
        });
    }

    public void DeletePosts(Post... posts)
    {
        UserRoomDB.databaseWriteExecutor.execute(()->{
            postDao.DeletePosts(posts);
        });
    }

    public void UpdatePost(Post post)
    {
        UserRoomDB.databaseWriteExecutor.execute(()->{
            postDao.UpdatePost(post);
        });
    }




}
