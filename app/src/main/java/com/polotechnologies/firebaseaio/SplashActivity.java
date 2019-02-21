package com.polotechnologies.firebaseaio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_launcherTheme);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startLoginActivity = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(startLoginActivity);
                finish();
            }
        }, 1000);
        super.onCreate(savedInstanceState);
    }
}
