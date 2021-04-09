package com.example.h1;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class ParseString {

    @RequiresApi(api = Build.VERSION_CODES.O)
    static public String parseProgress(int current, int id){
        int p = Math.round( (float)current / (float)id * 100);
        String percent = String.valueOf(p);

        return String.join("", percent, "%");
    }
}
