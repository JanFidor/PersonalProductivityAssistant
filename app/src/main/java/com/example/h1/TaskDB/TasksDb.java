package com.example.h1.TaskDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class TasksDb extends RoomDatabase {
    public abstract TaskDAO taskDAO();
}
