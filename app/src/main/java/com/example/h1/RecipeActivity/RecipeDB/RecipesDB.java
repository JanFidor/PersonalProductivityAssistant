package com.example.h1.RecipeActivity.RecipeDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
public abstract class RecipesDB extends RoomDatabase {
    public abstract RecipeDAO recipeDAO();
}
