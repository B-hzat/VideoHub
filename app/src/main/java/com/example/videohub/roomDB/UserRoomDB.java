package com.example.videohub.roomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.videohub.Entities.Post;
import com.example.videohub.Entities.User;
import com.example.videohub.dao.PostDao;
import com.example.videohub.dao.UserDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {User.class, Post.class}, version = 1, exportSchema = false)
public abstract class UserRoomDB extends RoomDatabase {

    public abstract UserDao getUserDao();
    public abstract PostDao getPostDao();

    private static UserRoomDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 5;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UserRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDB.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        UserRoomDB.class, "User_database")
                        .build();
            }

    }
        return INSTANCE;
    }

}
