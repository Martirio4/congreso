package com.nomad.audit5s.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.nomad.audit5s.R;
import com.nomad.audit5s.fragments.onBoarding.FragmentOb1;
import com.nomad.audit5s.fragments.onBoarding.FragmentOb2;
import com.nomad.audit5s.fragments.onBoarding.FragmentOb3;
import com.nomad.audit5s.fragments.onBoarding.FragmentOb4;
import com.nomad.audit5s.fragments.onBoarding.FragmentOb5;

public class ActivityOnboarding extends AppIntro2 {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.

        FragmentOb2 ob2= new FragmentOb2();
        FragmentOb3 ob3= new FragmentOb3();
        FragmentOb4 ob4= new FragmentOb4();
        FragmentOb5 ob5= new FragmentOb5();



        addSlide(ob3);
        addSlide(ob2);
        addSlide(ob4);
        addSlide(ob5);

        // OPTIONAL METHODS
        // Override bar/separator color.
        //setBarColor(ContextCompat.getColor(this,R.color.tile4));
        //setSeparatorColor(ContextCompat.getColor(this,R.color.blancoNomad));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(false);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        goLogin();
    }

    private void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        goLogin();
        ActivityOnboarding.this.finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}