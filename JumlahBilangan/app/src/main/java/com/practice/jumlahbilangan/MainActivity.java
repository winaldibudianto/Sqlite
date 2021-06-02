package com.practice.jumlahbilangan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText bil1;
    private EditText bil2;
    private EditText jumlah;
    private Button btnHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initUI();
        this.initEvent();
    }

    private void initUI() {
        this.bil1 = (EditText) findViewById(R.id.txtbil1);
        this.bil2 = (EditText) findViewById(R.id.txtbil2);
        this.jumlah = (EditText) findViewById(R.id.hasil);
        this.btnHasil = (Button) findViewById(R.id.btnbil);
    }

    private void initEvent() {
        this.btnHasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitungJumlah();
            }
        });
    }

    private void hitungJumlah() {
        int angka1 = Integer.parseInt(this.bil1.getText().toString());
        int angka2 = Integer.parseInt(this.bil2.getText().toString());
        int total = angka1 + angka2;
        this.jumlah.setText(total + "");
    }
}