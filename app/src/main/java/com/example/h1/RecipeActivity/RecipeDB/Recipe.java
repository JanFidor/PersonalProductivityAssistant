package com.example.h1.RecipeActivity.RecipeDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class Recipe {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "taste")
    public int taste;

    @ColumnInfo(name="type")
    public int type;

    @ColumnInfo(name="time")
    public int time;

    @ColumnInfo(name="url")
    public String url;
}
