package com.practice.latihanintent;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView t = (TextView) findViewById(R.id.text_viewdata);
        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString("name");
        t.setText(s);
    }
}
