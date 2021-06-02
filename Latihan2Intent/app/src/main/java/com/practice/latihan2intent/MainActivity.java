package com.practice.latihan2intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btnMoveActivity;
    public Button btnMoveWithDataActivity;
    public Button btnMoveWithObject;
    Button btnDialNumber;
    Button btnMoveResult;
    TextView tvResult;

    private int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnMoveActivity = (Button) findViewById(R.id.btn_move_activity);
        this.btnMoveActivity.setOnClickListener(this);

        this.btnMoveWithDataActivity = (Button) findViewById((R.id.btn_move_with_data_activity));
        this.btnMoveWithDataActivity.setOnClickListener(this);

        this.btnMoveWithObject = (Button) findViewById(R.id.btn_move_activity_object);
        this.btnMoveWithObject.setOnClickListener(this);

        this.btnDialNumber = (Button) findViewById(R.id.btn_dial_number);
        this.btnDialNumber.setOnClickListener(this);

        this.btnMoveResult = (Button) findViewById(R.id.btn_move_for_result);
        this.btnMoveResult.setOnClickListener(this);

        this.tvResult = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_move_activity:
                Intent moveIntent = new Intent(MainActivity.this, MoveActivity.class);
                startActivity(moveIntent);
                break;
            case R.id.btn_move_with_data_activity:
                Intent moveWithDataIntent = new Intent(MainActivity.this, MoveWithDataActivity.class);
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_NAME, "Faza Nurfaizi");
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_AGE, 17);
                startActivity(moveWithDataIntent);
                break;
            case R.id.btn_move_activity_object:
                Person person = new Person();
                person.setName("Faza nurfaizi");
                person.setAge(17);
                person.setEmail("nurfaizi.faza@gmail.com");
                person.setCity("Bandung");
                Intent moveWithObjectIntent = new Intent(MainActivity.this, MoveWithObjectActivity.class);
                moveWithObjectIntent.putExtra(MoveWithObjectActivity.EXTRA_PERSON, person);
                startActivity(moveWithObjectIntent);;
                break;
            case R.id.btn_dial_number:
                String phoneNumber = "123456789012";
                Intent dialPhoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(dialPhoneIntent);
                break;
            case R.id.btn_move_for_result:
                Intent moveForResultIntent = new Intent(MainActivity.this, MoveForResultActivity.class);
                startActivityForResult(moveForResultIntent, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE) {
            if(resultCode == MoveForResultActivity.RESULT_CODE) {
                int selectedValue = data.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0);
                tvResult.setText(String.format("Hasil : %s", selectedValue));
            }
        }
    }
}