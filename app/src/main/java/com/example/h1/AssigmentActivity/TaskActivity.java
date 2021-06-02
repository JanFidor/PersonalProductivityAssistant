package com.example.h1.AssigmentActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.h1.R;

public class TaskActivity extends AppCompatActivity {

    private EditText editText;
    private ImageView plusProgress, minusProgress;


    private TextView val;
    private EditText tot;

    private int id;
    private String name;
    private String n;
    private String total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        editText = findViewById(R.id.titleTask);
        minusProgress = findViewById(R.id.minusProgress);
        plusProgress = findViewById(R.id.plusProgress);
        val = findViewById(R.id.current);
        tot = findViewById(R.id.max);

        name = getIntent().getStringExtra("name");
        id = getIntent().getIntExtra("id", 0);
        n = getIntent().getStringExtra("n");
        total = getIntent().getStringExtra("total");

        editText.setText(name);
        val.setText(n);
        tot.setText(total);
    }


    @Override
    protected void onPause() {
        super.onPause();
        //TaskList.database.taskDAO().save(editText.getText().toString(), val.getText().toString(), tot.getText().toString(), id);
    }

}