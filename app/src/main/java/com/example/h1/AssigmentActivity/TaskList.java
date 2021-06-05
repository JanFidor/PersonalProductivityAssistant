package com.example.h1.AssigmentActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.h1.AssigmentActivity.TaskDB.TasksDb;
import com.example.h1.PopupFactory;
import com.example.h1.PopupImplementations.PopupInterface;
import com.example.h1.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


//TODO
//on button click, update progress bar, not entire adapter
public class TaskList extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static TaskListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static TasksDb database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_activity_main);
        setTheme(R.style.DarkTheme);


        // creates local database of tasks
        database = Room.databaseBuilder(getApplicationContext(), TasksDb.class, "notes")
                .allowMainThreadQueries()
                .build();

        // creates backend for Recycler View
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        adapter = new TaskListAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



        // button handling creation of new tasks
        FloatingActionButton button = findViewById(R.id.add_note_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupFactory popupFactory = new PopupFactory();
                popupFactory.getPopup("TasksCreate", v, -1);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.reload();
    }
}