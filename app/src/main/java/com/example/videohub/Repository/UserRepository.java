package com.example.videohub.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.videohub.Entities.User;
import com.example.videohub.dao.UserDao;
import com.example.videohub.roomDB.UserRoomDB;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> liveDataUser;

    public UserRepository(Application application)
    {
        UserRoomDB db= UserRoomDB.getDatabase(application.getApplicationContext());
        userDao=db.getUserDao();
        liveDataUser=userDao.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers()
    {
        return liveDataUser;
    }

    public LiveData<List<User>> FindUserById(int Id)
    {
        return userDao.FindUserById(Id);
    }


    public void InsertUser(User user)
    {
        UserRoomDB.databaseWriteExecutor.execute(()->{
            userDao.InsertUser(user);
        });
    }


    public void InsertUsers(User... users)
    {
        UserRoomDB.databaseWriteExecutor.execute(()->{
            userDao.InsertUsers(users);
        });
    }


    public void DeleteUser(User user)
    {
        UserRoomDB.databaseWriteExecutor.execute(()->{
            userDao.DeleteUser(user);
        });
    }


    public  void DeleteUsers(User... users)
    {
        UserRoomDB.databaseWriteExecutor.execute(()->{
            userDao.DeleteUsers(users);
        });
    }


    public void UpdateUser(User user)
    {
        UserRoomDB.databaseWriteExecutor.execute(()->{
            userDao.UpdateUser(user);
        });
    }



}
