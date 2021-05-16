package com.example.videohub.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.videohub.Entities.User;

import java.util.List;
@Dao
public interface UserDao {

    @Insert
    void InsertUser(User user);

    @Insert
    void InsertUsers(User... users);

    @Delete
    void DeleteUser(User user);

    @Delete
    void DeleteUsers(User... users);

    @Update
    void UpdateUser(User user);

    @Query("SELECT * FROM tblUser")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM tblUser WHERE userID = :Id")
    LiveData<List<User>> FindUserById(int Id);

}
