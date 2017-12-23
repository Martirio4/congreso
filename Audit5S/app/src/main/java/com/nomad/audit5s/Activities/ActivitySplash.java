package com.nomad.audit5s.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nomad.audit5s.R;

public class ActivitySplash extends AppCompatActivity {

    //DURACION DE DE LA ESPERA

    private final int SPLASH_DISPLAY_LENGHT = 1500;


    @Override
    protected void onCreate(Bundle splash) {
        super.onCreate(splash);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences settings = getSharedPreferences("prefs", 0);
                boolean firstRun = settings.getBoolean("firstRun", false);
                if (!firstRun)//if running for first time
                //Splash will load for first time
                {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("firstRun", true);
                    editor.commit();

                    Intent mainIntent = new Intent(ActivitySplash.this, ActivityOnboarding.class);
                    ActivitySplash.this.startActivity(mainIntent);
                    ActivitySplash.this.finish();
                }
                else {
                    Intent mainIntent = new Intent(ActivitySplash.this, LoginActivity.class);
                    ActivitySplash.this.startActivity(mainIntent);
                    ActivitySplash.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGHT);

    }

}
