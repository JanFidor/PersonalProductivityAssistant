package com.example.h1.TaskDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.h1.TaskDB.Value;
import com.example.h1.TaskDB.ValuesDAO;

@Database(entities = {Value.class}, version = 1, exportSchema = false)
public abstract class ValuesDb extends RoomDatabase {
    public abstract ValuesDAO valuesDAO();
}
