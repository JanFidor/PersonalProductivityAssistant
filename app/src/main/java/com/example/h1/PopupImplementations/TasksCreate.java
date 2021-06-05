package com.example.h1.PopupImplementations;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.h1.AssigmentActivity.TaskDB.Task;
import com.example.h1.AssigmentActivity.TaskList;
import com.example.h1.R;

public class TasksCreate implements PopupInterface{
    @Override
    public void create(final View view, final int id){
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.tasks_popup_create, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow -> focusable: true
        //Create a window with our parameters, deleted final
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);



        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler
        //final EditText editText = popupView.findViewById(R.id.titleTask);
        //editText.setText(R.string.DefName);

        // editText for assigning name to a task
        final EditText editText = popupView.findViewById(R.id.edit_comment);

        // choice of an amount of time needed to complete a task
        final NumberPicker numbPick = popupView.findViewById(R.id.edit_number);
        //numP.setValue(1);
        numbPick.setMinValue(1);
        numbPick.setMaxValue(100);
        numbPick.setWrapSelectorWheel(true);

        Button button1 = popupView.findViewById(R.id.buttonSubmit);
        Button button2 = popupView.findViewById(R.id.buttonCancel);

        // dismiss window
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        // confirm creating task
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Task created", Toast.LENGTH_SHORT).show();
                TaskList.database.taskDAO().make(editText.getText().toString(), 0, numbPick.getValue()); // adds task to the database
                popupWindow.dismiss();
                TaskList.adapter.reload();   // reloads RecyclerViews data set

            }
        });
    }
}
