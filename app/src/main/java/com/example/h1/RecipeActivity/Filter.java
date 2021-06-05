package com.example.h1.RecipeActivity;

import com.example.h1.RecipeActivity.RecipeDB.Recipe;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    /*
    public static List<Recipe> filter(List<Recipe> database, int taste, int type) {

        List<Recipe> filtered = new ArrayList<>();
        for (Recipe r : database) {
            boolean Taste =  taste == 0 || taste == r.taste;
            boolean Type =  type == 0 || type == r.type;
             if (Taste && Type) {
                 filtered.add(r);
             }
        }

        return filtered;*/
        public static List<Recipe> filter(List<Recipe> database, int taste, int type) {
            List<Recipe> filtered = new ArrayList<>();
            if (taste == 0 || type == 0){
                return filtered;
            }

            for (Recipe r : database) {
                //boolean Taste =  taste == 0 || taste == r.taste;
                //boolean Type =  type == 0 || type == r.type;
                if (taste == r.taste && type == r.type) {
                    filtered.add(r);
                }
            }

            return filtered;
        }

    /*
    public static List<Recipe> filter(List<Recipe> database) {

        List<Recipe> filtered = new ArrayList<>();
        int Taste = MainActivity.database.recipeDAO().getTaste(MainActivity.FILTER_KEY);
        int Type = MainActivity.database.recipeDAO().getType(MainActivity.FILTER_KEY);

        for (Recipe r : database){
            if (r.name.equals(MainActivity.FILTER_KEY))filtered.add(r);
            else if (Taste == 0 && MainActivity.Type == 0){
                filtered.add(r);
            }
            else if (Type == 0 && MainActivity.Type == r.taste){
                filtered.add(r);
            }
            else if(Taste == r.taste && Type == r.type){
                filtered.add(r);
            }
        }
        return filtered;
    }
     */

    //TODO group the list
    public void group(){
        return;
    }
}
