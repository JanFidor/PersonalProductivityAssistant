package com.example.h1.PomodoroActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.h1.R;
import com.example.h1.PomodoroActivity.ValuesDB.ValuesDb;


//TODO
//solve first value beeing wack, and stop the counter option
public class PomodoroManager extends AppCompatActivity {
    final long MILLI_IN_SET = 45*60*1000;   //number of milliseconds in 45 minutes
    long total_sets = 0;


    CountDownTimer timer;
    ValuesDb ValueDatabase;
    long time, minutes, seconds, b;
    TextView countDown;

    public PomodoroManager() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro_manager);
        setTheme(R.style.DarkTheme);

        // Creation of database holding information used by Pomodoro alarm
        ValueDatabase = Room.databaseBuilder(getApplicationContext(), ValuesDb.class, "values")
                .allowMainThreadQueries()
                .build();


        countDown = findViewById(R.id.CountDown);
        time = MILLI_IN_SET;
        countDown.setText(edited(time));

        // boolean tracking if timer runs in the background
        //      0 -> timer is stopped
        //      1 -> timer is running
        b = 0;

        // Creation of appropriate data for the PA database
        ValueDatabase.valuesDAO().make("timeLeft", time);   // tracks time left in the PA
        ValueDatabase.valuesDAO().make("counting", b);      // tracks whether PA was running in the background
        ValueDatabase.valuesDAO().make("TotalSets", 0); // tracks the amount of finished Pomodoro sets
        ValueDatabase.valuesDAO().make("timeStopped", TimeManager.getCurrent()); // tracks when the app was minimalized to calculate time passed


        // creates PA timer with according time
        updateTimer(time);

        //  Click Listener used to stop and resume the timer
        countDown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // tracks which action should be executed by accessing timers status (if it's running)
                b = ValueDatabase.valuesDAO().getValue("counting");

                // timer is stopped, therefore it's being resumed
                if (b == 0){
                    StartTheCount();
                    Log.d("PA_Status", "Resuming the timer");
                }
                // timer is running, therefore it's being stopped
                else {
                    StopTheCount(true);
                    Log.d("PA_Status", "Stopping the timer");
                    //Toast.makeText(v.getContext(), "Count stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //  Long Click Listener used to restart the timer
        countDown.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                StopTheCount(true);
                countDown.setText(edited(MILLI_IN_SET));
                ValueDatabase.valuesDAO().updateN(MILLI_IN_SET, "timeLeft");
                Toast.makeText(countDown.getContext(), "Count Restarted", Toast.LENGTH_SHORT).show();

                Log.d("PA_Status", "Restarting the timer");

                return true;
            }
        });
    }



    // actions executed every time PA feature is opened
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onResume() {
        super.onResume();
        TextView countDown = findViewById(R.id.CountDown);
        // getting time left before PA window was closed from database
        long time = ValueDatabase.valuesDAO().getValue("timeLeft");

        // calculating how much time elapsed since closing window and how much time was left on timer
        long timeStopped = ValueDatabase.valuesDAO().getValue("timeStopped");
        long delta = TimeManager.timeChange(timeStopped);

        long counting = ValueDatabase.valuesDAO().getValue("counting");
        // checks if the change in time affected timer -> only if it was counting when closed
        if (counting == 1) {
            // if the alarm ~ finished, the timer is restarted with one second left
            time = Math.max(1000, time - delta);
            ValueDatabase.valuesDAO().updateN(time, "timeLeft");
            StartTheCount();
        }

        countDown.setText(edited(time));
    }

    @Override
    protected void onPause() {
        super.onPause();
        StopTheCount(false);

        //updates information when the app was closed
        ValueDatabase.valuesDAO().updateN(TimeManager.getCurrent(), "timeStopped");
        //ValueDatabase.close(); TODO
    }

    // starts the timer countdown
    protected void StartTheCount(){
        ValueDatabase.valuesDAO().updateN(1, "counting");
        time = ValueDatabase.valuesDAO().getValue("timeLeft");
        updateTimer(time);
        timer.start();
    }

    // stop -> used to track whether the alarm should stop counting (when closed)
    // closes the timer
    protected void StopTheCount(boolean stop){
        if (stop) ValueDatabase.valuesDAO().updateN(0, "counting");
        //if (!stop) ValueDatabase.valuesDAO().updateN(1, "counting");
        if (timer != null){
            timer.cancel();
            timer = null;
        }
        Log.e("start", "stopped1");
    }


    // formats amount of miliseconds left to "minutes : seconds" string with forward 0s
    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected String edited(long time){

        minutes = time / (60 * 1000);
        seconds = (time / 1000) % 60;
        return  String.join(" ", String.format("%02d", minutes), ":", String.format("%02d", seconds));    //force 0 to show as 00
    }


    //creates a new timer with 'time' parameter (milliseconds)
    void updateTimer (long time){
        Log.d("PA_Status", "Creating timer");
        countDown = findViewById(R.id.CountDown);
        timer = new CountDownTimer(time, 1000) {
            @RequiresApi(api = Build.VERSION_CODES.O)

            // updates textView of time remaining and database every second
            public void onTick(long millisUntilFinished) {
                countDown.setText(edited(millisUntilFinished));
                ValueDatabase.valuesDAO().updateN(millisUntilFinished, "timeLeft");
            }

            // updates the database, starts vibrations and
            // displays information about number of finished sets after timer finishes
                public void onFinish() {
                // call to database to stop the countdown
                ValueDatabase.valuesDAO().updateN(0, "counting");
                Log.d("PA_Status", "Timer finished");

                // starts vibrations
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(3000);

                // display how many PA sets were finished in total
                total_sets = ValueDatabase.valuesDAO().getValue("TotalSets") + 1;
                ValueDatabase.valuesDAO().updateN(total_sets, "TotalSets");
                String tmp = total_sets + "";
                Toast.makeText(countDown.getContext(), "Nice, you have in total finished " + tmp + " sets", Toast.LENGTH_SHORT).show();

                // restarts the amount of time left on timer
                ValueDatabase.valuesDAO().updateN(MILLI_IN_SET, "timeLeft");
                //TODO  make vibrations till restarted
            }
        };
    }
}