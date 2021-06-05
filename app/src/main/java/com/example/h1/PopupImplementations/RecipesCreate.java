package com.example.h1.PopupImplementations;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.h1.AssigmentActivity.TaskList;
import com.example.h1.R;
import com.example.h1.RecipeActivity.RecipeDB.Recipe;
import com.example.h1.RecipeActivity.RecipeList;
import com.example.h1.RecipeActivity.Values;

public class RecipesCreate implements PopupInterface {
    @Override
    public void create(final View view, final int id){
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.recipes_popup_create, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow -> focusable: true
        //Create a window with our parameters, deleted final
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);



        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        final EditText editTextName = popupView.findViewById(R.id.recipe_make_name);
        final EditText editTextUrl = popupView.findViewById(R.id.recipe_make_url);
        final Spinner spinnerTasteAdd = popupView.findViewById(R.id.spinner_taste);
        final Spinner spinnerTypeAdd = popupView.findViewById(R.id.spinner_type);
        final Spinner spinnerTimeAdd = popupView.findViewById(R.id.spinner_time);
        final Button buttonAdd = popupView.findViewById(R.id.recipe_add);
        final Button buttonCancel = popupView.findViewById(R.id.recipe_cancel);
        final TextView text = popupView.findViewById(R.id.TasteText);
        // TODO assign values to a hashmap


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String url = editTextUrl.getText().toString();
                String taste = spinnerTasteAdd.getSelectedItem().toString();
                String type = spinnerTypeAdd.getSelectedItem().toString();
                String time = spinnerTimeAdd.getSelectedItem().toString();

                int tasteID = Values.getTaste(taste, v.getContext());
                int typeID = Values.getType(type, v.getContext());
                int timeID = Values.getTime(time, v.getContext());

                if (URLUtil.isValidUrl(url)){
                    RecipeList.database.recipeDAO().add(name, tasteID, typeID, timeID, url);
                    popupWindow.dismiss();
                    RecipeList.adapter.reload(RecipeList.Taste, RecipeList.Type);
                }
                else{
                    Toast.makeText(text.getContext(), "Incorrect URL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}
