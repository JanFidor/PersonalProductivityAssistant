package com.example.h1.RecipeActivity.RecipeDB;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDAO {
    @Query("INSERT INTO recipes (name, taste, type, time, url) VALUES (:name, :taste, :type, :time, :url)")
    void add(String name, int taste, int type, int time, String url);

    @Query("SELECT * FROM recipes ")
    List<Recipe> getAll();

    @Query("SELECT * FROM recipes WHERE id =:id")
    Recipe get(int id);

    @Query("DELETE FROM recipes WHERE id = :id")
    void delete(int id);

    //TODO supplement with one query returning list size 1, of recipe
    @Query("SELECT taste FROM recipes WHERE name =:name")
    int getTaste(String name);

    @Query("SELECT * FROM recipes WHERE name =:name")
    int getType(String name);

    @Query("UPDATE recipes SET taste =:taste, type =:type WHERE name = :name")
    void updateFilter(String name, int taste, int type);


}
