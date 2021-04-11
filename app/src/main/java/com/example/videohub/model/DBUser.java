package com.example.videohub.model;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBUser {
    static ArrayList<User> userArrayList;

    public DBUser(){userArrayList=new ArrayList<User>();}

    public int AddUser(User user){
        DBUser.userArrayList.add(user);
        return 1;
    }

    public ArrayList<User> GetAllUsers(){
        return userArrayList;
    }

    public boolean DeleteUser(User user){
        userArrayList.remove(user);
        return true;
    }

    public static boolean Login(String email, String password){
        boolean result=false;


        if(userArrayList!=null){
            for(User user: userArrayList){
                if (user.getUsername().toLowerCase().equals( email.toLowerCase()) && user.getPassword().equals(password.toLowerCase())) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

}
