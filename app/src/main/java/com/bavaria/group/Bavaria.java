package com.bavaria.group;

import android.app.Application;
import android.support.multidex.MultiDex;

/**
 * Created by ravi-archi on 3/31/2017.
 */

public class Bavaria extends Application {

        @Override
        public void onCreate() {
            super.onCreate();
            MultiDex.install(getApplicationContext());
        }
    }
