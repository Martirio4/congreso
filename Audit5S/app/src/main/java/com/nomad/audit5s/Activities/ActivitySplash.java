package com.nomad.audit5s.activities;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.nomad.audit5s.BuildConfig;
import com.nomad.audit5s.R;

public class ActivitySplash extends AppCompatActivity {

    //DURACION DE DE LA ESPERA

    private final int SPLASH_DISPLAY_LENGHT = 1500;


    @Override
    protected void onCreate(Bundle splash) {
        super.onCreate(splash);
        setContentView(R.layout.activity_splash);
        TextView version=findViewById(R.id.versionApp);
        version.setText(BuildConfig.VERSION_NAME);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

              Intent mainIntent = new Intent(ActivitySplash.this,LoginActivity.class);
              ActivitySplash.this.startActivity(mainIntent);
              ActivitySplash.this.finish();

            }
        }, SPLASH_DISPLAY_LENGHT);

    }


}
