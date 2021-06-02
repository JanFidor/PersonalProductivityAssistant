package com.example.h1.PomodoroActivity;

public class TimeManager {

    static public long getCurrent(){
        return System.currentTimeMillis();
    }

    static public long timeChange(long timeLeft){
        long t1 = getCurrent();
        return t1 - timeLeft;
    }
    //static public void

    /*
    long timeStopped = ValueDatabase.valuesDAO().getValue("timeStopped");
        long delta = TimeManager.timeChange(timeStopped);
        long counting = ValueDatabase.valuesDAO().getValue("counting");
        if (counting == 1){
            time = Math.max(1000, time - delta);
    */
}
