package com.practice.linearrelative;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button daftarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.daftarBtn = findViewById(R.id.daftar_btn);
        this.daftarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDisplay();
            }
        });
    }

    protected void changeDisplay() {
        Intent intent = new Intent(MainActivity.this, Profile.class);
        startActivity(intent);
    }
}