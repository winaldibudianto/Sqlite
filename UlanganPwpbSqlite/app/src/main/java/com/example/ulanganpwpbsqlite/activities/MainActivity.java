package com.example.ulanganpwpbsqlite.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.example.ulanganpwpbsqlite.R;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeActivityIntent = new Intent(MainActivity.this, HomeActivity.class);
                progressBar.setVisibility(View.GONE);
                startActivity(homeActivityIntent);
                finish();
            }
        }, 2500);
    }
}