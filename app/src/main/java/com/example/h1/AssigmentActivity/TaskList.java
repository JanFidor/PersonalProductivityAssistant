package com.example.h1.AssigmentActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.h1.R;
import com.example.h1.TaskDB.TasksDb;
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
        setContentView(R.layout.activity_task_list);
        setTheme(R.style.DarkTheme);

        database = Room.databaseBuilder(getApplicationContext(), TasksDb.class, "notes")
                .allowMainThreadQueries()
                .build();

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        adapter = new TaskListAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        FloatingActionButton button = findViewById(R.id.add_note_button);

        Context context = this;


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialogBuilder = new AlertDialog.Builder(TaskList.this).create();
                LayoutInflater inflater = TaskList.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.window_popup, null);

                final EditText editText = (EditText) dialogView.findViewById(R.id.edit_comment);

                final NumberPicker numbPick = dialogView.findViewById(R.id.edit_number);
                //numP.setValue(1);
                numbPick.setMinValue(1);
                numbPick.setMaxValue(100);
                numbPick.setWrapSelectorWheel(true);

                Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
                Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);

                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                });
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // DO SOMETHINGS
                        Toast.makeText(view.getContext(), "Task created", Toast.LENGTH_SHORT).show();
                        database.taskDAO().make(editText.getText().toString(), 0, numbPick.getValue());
                        dialogBuilder.dismiss();
                        adapter.reload();
                    }
                });

                dialogBuilder.setView(dialogView);
                dialogBuilder.show();

                //database.taskDAO().create();
                //adapter.reload();


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.reload();
    }
}