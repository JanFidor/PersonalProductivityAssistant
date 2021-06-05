package com.example.h1.PopupImplementations;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.h1.AssigmentActivity.TaskList;
import com.example.h1.R;

public class TasksDelete implements PopupInterface {
    @Override
    public void create(final View view, final int id){
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.tasks_popup_delete, null);

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

        Button verifyD = popupView.findViewById(R.id.deleteYes);
        Button cancelD = popupView.findViewById(R.id.deleteNo);

        verifyD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskList.database.taskDAO().delete(id);
                TaskList.adapter.reload();
                popupWindow.dismiss();
                Toast.makeText(view.getContext(), "Task removed", Toast.LENGTH_SHORT).show();


            }
        });
        //Handler for clicking on the inactive zone of the window

        cancelD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //Toast.makeText(view.getContext(), "Wow, popup action button", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
