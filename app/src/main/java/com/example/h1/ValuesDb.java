package com.example.h1;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.h1.TaskDB.Task;
import com.example.h1.TaskDB.TaskDAO;

@Database(entities = {Value.class}, version = 1, exportSchema = false)
public abstract class ValuesDb extends RoomDatabase {
    public abstract ValuesDAO valuesDAO();
}
