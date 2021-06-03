package com.example.h1.PomodoroActivity.ValuesDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.h1.PomodoroActivity.ValuesDB.Value;
import com.example.h1.PomodoroActivity.ValuesDB.ValuesDAO;

@Database(entities = {Value.class}, version = 1, exportSchema = false)
public abstract class ValuesDb extends RoomDatabase {
    public abstract ValuesDAO valuesDAO();
}
