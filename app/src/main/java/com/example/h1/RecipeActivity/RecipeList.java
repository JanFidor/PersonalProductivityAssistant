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
                final AlertDialog dialogBuilder = new AlertDialog.Builder(RecipeList.this).create();
                LayoutInflater inflater = RecipeList.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.recipes_popup_filter, null);

                final Spinner spinnerTaste = dialogView.findViewById(R.id.spinner_taste);
                final Spinner spinnerType = dialogView.findViewById(R.id.spinner_type);

                dialogBuilder.setView(dialogView);
                dialogBuilder.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
                dialogBuilder.getWindow().setDimAmount(0.0f);


                dialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        String taste = spinnerTaste.getSelectedItem().toString();
                        String type = spinnerType.getSelectedItem().toString();

                        /*Log.e("values", taste + ' ' + type);
                        Type = Values.getTaste(taste);
                        Taste = Values.getType(type);

                        Log.e("inside", String.valueOf(tasteV) + ' ' + String.valueOf(typeV));
                        spinnerTaste.setSelection(tasteV);
                        spinnerType.setSelection(typeV);

                        database.recipeDAO().updateFilter("Filter", tasteV, typeV);
                        */    // again old shit

                        Taste = Values.getTaste(taste, RecipeList.this);
                        Type = Values.getType(type, RecipeList.this);
                        dialogBuilder.dismiss();
                        adapter.reload(Taste, Type);

                    }
                });

                dialogBuilder.show();


                //dialogBuilder.show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog recipeBuilder = new AlertDialog.Builder(RecipeList.this).create();
                LayoutInflater inflater = RecipeList.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.recipes_popup_create, null);

                final EditText editTextName = dialogView.findViewById(R.id.recipe_make_name);
                final EditText editTextUrl = dialogView.findViewById(R.id.recipe_make_url);
                final Spinner spinnerTasteAdd = dialogView.findViewById(R.id.spinner_taste);
                final Spinner spinnerTypeAdd = dialogView.findViewById(R.id.spinner_type);
                final Spinner spinnerTimeAdd = dialogView.findViewById(R.id.spinner_time);
                final Button buttonAdd = dialogView.findViewById(R.id.recipe_add);
                // TODO assign values to a hashmap

                recipeBuilder.setView(dialogView);

                recipeBuilder.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
                recipeBuilder.getWindow().setDimAmount(0.0f);


                buttonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = editTextName.getText().toString();
                        String url = editTextUrl.getText().toString();
                        String taste = spinnerTasteAdd.getSelectedItem().toString();
                        String type = spinnerTypeAdd.getSelectedItem().toString();
                        String time = spinnerTimeAdd.getSelectedItem().toString();

                        int tasteID = Values.getTaste(taste, RecipeList.this);
                        int typeID = Values.getType(type, RecipeList.this);
                        int timeID = Values.getTime(time, RecipeList.this);

                        RecipeList.database.recipeDAO().add(name, tasteID, typeID, timeID, url);
                        recipeBuilder.dismiss();
                        adapter.reload(Taste, Type);
                    }
                });

                recipeBuilder.show();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        adapter.reload(Taste, Type);
    }

}

