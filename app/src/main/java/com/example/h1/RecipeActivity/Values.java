package com.example.h1.RecipeActivity;

import android.content.Context;
import android.content.res.Resources;

import com.example.h1.R;

import java.util.HashMap;


// TODO limit choice for create to one without "any"
public class Values {
    public static int getTaste(String taste, Context context){
        String[] tastes = context.getResources().getStringArray(R.array.tastes);
        HashMap<String, Integer> tasteMap = new HashMap<>();
        Integer i = 0;
        for (String t: tastes) {
            tasteMap.put(t, i);
            i += 1;
        }

        return tasteMap.get(taste);
    }


    public static int getType(String type, Context context){
        String[] tastes = context.getResources().getStringArray(R.array.types);
        HashMap<String, Integer> typeMap = new HashMap<>();
        Integer i = 0;
        for (String t: tastes) {
            typeMap.put(t, i);
            i += 1;
        }

        return typeMap.get(type);
    }
    public static int getTime(String time, Context context){
        String[] tastes = context.getResources().getStringArray(R.array.times);
        HashMap<String, Integer> timeMap = new HashMap<>();
        Integer i = 0;
        for (String t: tastes) {
            timeMap.put(t, i);
            i += 1;
        }

        return timeMap.get(time);
    }

    public static String timeString(int time, Context context){
        String[] tastes = context.getResources().getStringArray(R.array.times);
        HashMap<Integer, String> timeMap = new HashMap<>();
        Integer i = 0;
        for (String t: tastes) {
            timeMap.put(i, t);
            i += 1;
        }

        return timeMap.get(time);
    }

    //TODO add different colors for times
    public static void getColor(int time){};


}
