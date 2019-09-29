package com.decimals.stuhub;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ADARSH ANURAG on 02-04-2018.
 */

public class SplashActivity extends AppCompatActivity {

    long animDuration = 1000;
    private ImageView imageView;
    private ProgressBar progressBar;
    private TextView loadCount;
    int progress = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadCount=(TextView)findViewById(R.id.loadCount);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();
    }

    private void doWork() {
        for (;progress <= 100; progress += 1) {
            handler.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    loadCount.setText(String.valueOf(progress) + "%");
                    progressBar.setProgress(progress);

                }
            });
            try {

                Thread.sleep(36);

                //loadCount.setText(progress);
//                loadCount.setVisibility(View.GONE);
//                loadCount.setText(String.valueOf(progress)+"%");
//                loadCount.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void startApp() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        SplashActivity.this.startActivity(intent);
    }
}
