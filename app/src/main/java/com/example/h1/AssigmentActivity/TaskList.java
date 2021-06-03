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

        FloatingActionButton button = findViewById(R.id.add_note_button);


        // button handling creation of new tasks
        //TODO custom factory class for AlertDialog windows
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialogBuilder = new AlertDialog.Builder(TaskList.this).create();
                LayoutInflater inflater = TaskList.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.tasks_popup_create, null);

                // editText for assigning name to a task
                final EditText editText = dialogView.findViewById(R.id.edit_comment);

                // choice of an amount of time needed to complete a task
                final NumberPicker numbPick = dialogView.findViewById(R.id.edit_number);
                //numP.setValue(1);
                numbPick.setMinValue(1);
                numbPick.setMaxValue(100);
                numbPick.setWrapSelectorWheel(true);

                Button button1 = dialogView.findViewById(R.id.buttonSubmit);
                Button button2 = dialogView.findViewById(R.id.buttonCancel);

                // dismiss window
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                });

                // confirm creating task
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), "Task created", Toast.LENGTH_SHORT).show();
                        database.taskDAO().make(editText.getText().toString(), 0, numbPick.getValue()); // adds task to the database
                        dialogBuilder.dismiss();
                        adapter.reload();   // reloads RecyclerViews data set
                    }
                });

                dialogBuilder.setView(dialogView);
                dialogBuilder.show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.reload();
    }
}