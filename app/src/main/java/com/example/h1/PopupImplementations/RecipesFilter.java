package com.example.h1.PopupImplementations;

import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PaintDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.example.h1.AssigmentActivity.TaskList;
import com.example.h1.PopupFactory;
import com.example.h1.R;
import com.example.h1.RecipeActivity.RecipeDB.Recipe;
import com.example.h1.RecipeActivity.RecipeList;
import com.example.h1.RecipeActivity.Values;

public class RecipesFilter implements PopupInterface {
    @Override
    public void create(final View view, final int id){
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.recipes_popup_filter, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow -> focusable: true
        //Create a window with our parameters, deleted final
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        final Spinner spinnerTaste = popupView.findViewById(R.id.spinner_taste);
        final Spinner spinnerType = popupView.findViewById(R.id.spinner_type);

        LinearLayout layout = popupView.findViewById(R.id.linear);
        // Tried OnDismissListener, couldn't dismiss

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                RecipeList.Taste = Values.getTaste(taste, view.getContext());
                RecipeList.Type = Values.getType(type, view.getContext());
                RecipeList.adapter.reload(RecipeList.Taste, RecipeList.Type);
                popupWindow.dismiss();
            }
        });
    }
}
