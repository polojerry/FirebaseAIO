package com.polotechnologies.firebaseaio;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent startLoginActivity = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(startLoginActivity);
        finish();
    }
}
