package com.practice.latihan2intent;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MoveWithDataActivity extends AppCompatActivity {

    public static String EXTRA_AGE = "extra age";
    public static String EXTRA_NAME = "extra name";
    private TextView tvDataReceived;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_with_data);
        this.tvDataReceived = (TextView) findViewById(R.id.tv_data_received);
        String name = getIntent().getStringExtra(EXTRA_NAME);
        int age = getIntent().getIntExtra(EXTRA_AGE, 0);
        String text = "Name : " + name + ", Your Age : " + age;
        this.tvDataReceived.setText(text);
    }
}
