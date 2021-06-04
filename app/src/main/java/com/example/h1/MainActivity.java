package com.example.h1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.h1.AssigmentActivity.TaskList;
import com.example.h1.PomodoroActivity.PomodoroManager;
import com.example.h1.RecipeActivity.RecipeList;

public class MainActivity extends AppCompatActivity {
    // Main activity used to navigate to specific features

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DarkTheme);
        setContentView(R.layout.main_activity_main);



        // Button directing user to the Note oriented feature
        ImageView button_assignments = findViewById(R.id.AssignmentActivity);
        button_assignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TaskList.class);
                v.getContext().startActivity(intent);
            }
        });


        // Button directing user to the Pomodoro oriented feature
        ImageView button_pomodoro = findViewById(R.id.PomodoroActivity);
        button_pomodoro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PomodoroManager.class);
                v.getContext().startActivity(intent);
            }
        });

        // Button directing user to the Recipe oriented feature
        ImageView button_resipes = findViewById(R.id.RecipesActivity);
        button_resipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RecipeList.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}