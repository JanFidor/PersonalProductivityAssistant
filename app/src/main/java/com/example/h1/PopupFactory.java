package com.example.h1;

import android.util.Log;
import android.view.View;

import com.example.h1.PopupImplementations.RecipesCreate;
import com.example.h1.PopupImplementations.RecipesDelete;
import com.example.h1.PopupImplementations.RecipesFilter;
import com.example.h1.PopupImplementations.TasksCreate;
import com.example.h1.PopupImplementations.TasksDelete;

public class PopupFactory {
    // naming convention: PackagePurpose
    public void getPopup(String type, final View view, final int id){
        if (type == null){
            Log.e("PopupFactory", "null given");
        }
        else if (type.equals("TasksDelete")){
            Log.d("PopupFactory", "TasksDelete invoked");
            new TasksDelete().create(view, id);
        }
        else if (type.equals("TasksCreate")){
            Log.d("PopupFactory", "TasksCreate invoked");
            new TasksCreate().create(view, id);
        }
        else if (type.equals("RecipesDelete")){
            Log.d("PopupFactory", "RecipesDelete invoked");
            new RecipesDelete().create(view, id);
        }
        else if (type.equals("RecipesCreate")){
            Log.d("PopupFactory", "RecipesCreate invoked");
            new RecipesCreate().create(view, id);
        }
        else if (type.equals("RecipesFilter")){
            Log.d("PopupFactory", "RecipesFilter invoked");
            new RecipesFilter().create(view, id);
        }
        else{
            Log.e("PopupFactory", "wrong key given");
        }

    }
}
