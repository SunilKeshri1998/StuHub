package com.decimals.stuhub;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Shubham on 09-04-2018.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
