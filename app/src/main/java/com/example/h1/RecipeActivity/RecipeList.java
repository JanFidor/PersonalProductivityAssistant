package com.example.h1.RecipeActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.h1.PopupFactory;
import com.example.h1.R;
import com.example.h1.RecipeActivity.RecipeDB.Recipe;
import com.example.h1.RecipeActivity.RecipeDB.RecipesDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

// Created by Jan Fidor
public class RecipeList extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static RecipeListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static RecipesDB database;

    public static int Type = 0;
    public static int Taste = 0;
    //public static String FILTER_KEY = "DatabaseFilterKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_activity_main);

        //create database of recipes
        //TODO make themes in R for text TextView and Buttons
        //TODO present textView + Spinner, 2 x buttons as 1 x LinearLayout
        //TODO why buttons in recipes_row_recipe?????
        //TODO change relative layout to linear layout of linear layoutss -> TODO 2)

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        adapter = new RecipeListAdapter();

        database = Room.databaseBuilder(this, RecipesDB.class, "recipes")
                .allowMainThreadQueries()
                .build();


/*
        if (getPreferences(MODE_PRIVATE).getBoolean("is_first_run", true)) {
            database.recipeDAO().add(FILTER_KEY, 0, 0, 0, "");
            getPreferences(MODE_PRIVATE).edit().putBoolean("is_first_run", false).commit();

        }*/


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ImageButton settingButton = findViewById(R.id.settings_button);
        settingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                PopupFactory popupFactory = new PopupFactory();
                popupFactory.getPopup("RecipesFilter", v, - 1);

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupFactory popupFactory = new PopupFactory();
                popupFactory.getPopup("RecipesCreate", v, -1);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        adapter.reload(Taste, Type);
    }

}

