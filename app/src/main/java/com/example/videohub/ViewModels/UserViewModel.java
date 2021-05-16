package com.example.videohub.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.videohub.Entities.User;
import com.example.videohub.Repository.UserRepository;
import com.example.videohub.roomDB.UserRoomDB;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<List<User>> liveDataUser;

    public UserViewModel(@NonNull Application application) {
        super(application);

        userRepository=new UserRepository(application);
        liveDataUser=userRepository.getAllUsers();

    }

    public LiveData<List<User>> getAllUsers()
    {
        return liveDataUser;
    }

    public LiveData<List<User>> FindUserById(int Id)
    {
        return userRepository.FindUserById(Id);
    }


    public void InsertUser(User user)
    {
            userRepository.InsertUser(user);
    }


    public void InsertUsers(User... users)
    {
            userRepository.InsertUsers(users);
    }


    public void DeleteUser(User user)
    {
            userRepository.DeleteUser(user);
    }


    public  void DeleteUsers(User... users)
    {
            userRepository.DeleteUsers(users);
    }


    public void UpdateUser(User user)
    {
            userRepository.UpdateUser(user);
    }

}
