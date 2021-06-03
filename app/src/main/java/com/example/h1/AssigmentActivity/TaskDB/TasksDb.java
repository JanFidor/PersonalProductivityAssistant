package com.example.h1.AssigmentActivity.TaskDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.h1.AssigmentActivity.TaskDB.Task;
import com.example.h1.AssigmentActivity.TaskDB.TaskDAO;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class TasksDb extends RoomDatabase {
    public abstract TaskDAO taskDAO();
}
