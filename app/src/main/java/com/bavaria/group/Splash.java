package com.bavaria.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by archirayan on 2/23/2016.
 */

public class Splash extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splush);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent in = new Intent(Splash.this, MainActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}
