package com.example.h1.TaskDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Task {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "contents")
    public String contents;

    @ColumnInfo(name = "spent")
    public int spent;

    @ColumnInfo(name = "total")
    public int total;

}