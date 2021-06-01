package com.example.h1;

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

import com.example.h1.TaskDB.ValuesDb;


//TODO
//solve first value beeing wack, and stop the counter option
public class PomodoroManager extends AppCompatActivity {
    final long MAGIC_NUMBER = 45*60*1000;   //milliseconds in 45 minutes
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


        ValueDatabase = Room.databaseBuilder(getApplicationContext(), ValuesDb.class, "values")
                .allowMainThreadQueries()
                .build();

        countDown = findViewById(R.id.CountDown);
        countDown.setText(edited(time));    //edited - method
        time = MAGIC_NUMBER;
        b = 0;
        ValueDatabase.valuesDAO().make("timeLeft", time);
        ValueDatabase.valuesDAO().make("counting", b);
        ValueDatabase.valuesDAO().make("TotalSets", 0);

        ValueDatabase.valuesDAO().make("timeStopped", TimeManager.getCurrent()); ///changing

        updateTimer(time);

        countDown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                b = ValueDatabase.valuesDAO().getValue("counting");
                if (b == 0){
                    StartTheCount();
                    //Toast.makeText(v.getContext(), "Count started", Toast.LENGTH_SHORT).show();
                }
                else {
                    StopTheCount(true);
                    //Toast.makeText(v.getContext(), "Count stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });

        countDown.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                StopTheCount(true);
                countDown.setText(edited(MAGIC_NUMBER));
                ValueDatabase.valuesDAO().updateN(MAGIC_NUMBER, "timeLeft");
                Toast.makeText(countDown.getContext(), "Count Restarted", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
    /*
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onStart() {
        super.onStart();
        TextView countDown = findViewById(R.id.CountDown);
        long time = ValueDatabase.valuesDAO().getValue("timeLeft");

        long timeStopped = ValueDatabase.valuesDAO().getValue("timeStopped");
        long delta = TimeManager.timeChange(timeStopped);
        long counting = ValueDatabase.valuesDAO().getValue("counting");
        if (counting == 1) {
            time = Math.max(1000, time - delta);
            Log.e("p", "Time delta");
        }

        countDown.setText(edited(time));
    }
*/
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onResume() {
        super.onResume();
        TextView countDown = findViewById(R.id.CountDown);
        long time = ValueDatabase.valuesDAO().getValue("timeLeft");

        long timeStopped = ValueDatabase.valuesDAO().getValue("timeStopped");
        long delta = TimeManager.timeChange(timeStopped);
        long counting = ValueDatabase.valuesDAO().getValue("counting");
        if (counting == 1) {
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

        ValueDatabase.valuesDAO().updateN(TimeManager.getCurrent(), "timeStopped");
        //ValueDatabase.close(); TODO
    }

    protected void StartTheCount(){
        ValueDatabase.valuesDAO().updateN(1, "counting");
        time = ValueDatabase.valuesDAO().getValue("timeLeft");
        updateTimer(time);
        timer.start();
    }

    protected void StopTheCount(boolean stop){
        if (stop) ValueDatabase.valuesDAO().updateN(0, "counting");
        //if (!stop) ValueDatabase.valuesDAO().updateN(1, "counting");
        if (timer != null){
            timer.cancel();
            timer = null;
        }
        Log.e("start", "stopped1");
    }

    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected String edited(long time){

        minutes = time / (60 * 1000);
        seconds = (time / 1000) % 60;
        return  String.join(" ", String.format("%02d", minutes), ":", String.format("%02d", seconds));    //force 0 to show as 00
    }

    void updateTimer (long time){
        countDown = findViewById(R.id.CountDown);
        timer = new CountDownTimer(time, 1000) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onTick(long millisUntilFinished) {
                long timeLeft = millisUntilFinished;
                Log.d("x","time passing");
                //Toast.makeText(countDown.getContext(), String.valueOf(ValueDatabase.valuesDAO().getValue("timeLeft")), Toast.LENGTH_SHORT).show();
                countDown.setText(edited(timeLeft));
                ValueDatabase.valuesDAO().updateN(timeLeft, "timeLeft");
            }
            public void onFinish() {
                ValueDatabase.valuesDAO().updateN(0, "counting");
                long t = ValueDatabase.valuesDAO().getValue("timeLeft");
                Log.e("start", "stopped2");
                Log.e("start", String.valueOf(t));

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(3000);

                total_sets = ValueDatabase.valuesDAO().getValue("TotalSets") + 1;
                ValueDatabase.valuesDAO().updateN(total_sets, "TotalSets");
                String tmp = total_sets + "";
                Toast.makeText(countDown.getContext(), "Nice, you have in total finished " + tmp + " sets", Toast.LENGTH_SHORT).show();

                //if (t < 2000) {
                //Toast.makeText(countDown.getContext(), "Nice", Toast.LENGTH_SHORT).show();
                ValueDatabase.valuesDAO().updateN(MAGIC_NUMBER, "timeLeft");
                //} // TODO clean this mess of a code and make vibrations till restarted
            }
        };
    }
}