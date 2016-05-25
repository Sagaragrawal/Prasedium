package com.firefield.praesidium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task

            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();

        }

        else {
            Intent intent1 = new Intent(MainActivity.this, start.class);
            startActivity(intent1);
        }

    }
}
