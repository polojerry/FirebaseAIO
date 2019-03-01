package com.polotechnologies.firebaseaio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent startLoginActivity = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(startLoginActivity);
        finish();
    }
}
