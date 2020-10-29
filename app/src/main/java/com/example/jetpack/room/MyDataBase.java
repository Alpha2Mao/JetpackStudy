package com.example.jetpack.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Student.class}, version = 1)
public abstract class MyDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "my_db";
    private static MyDataBase dataBaseInstance;
    public static synchronized MyDataBase getInstance(Context context){
        if (dataBaseInstance == null){
            dataBaseInstance = Room.databaseBuilder(context.getApplicationContext(), MyDataBase.class,
                    DATABASE_NAME).build();
        }
        return dataBaseInstance;
    }

    public abstract StudentDao studentDao();
}
