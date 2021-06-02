package com.example.ulanganpwpbsqlite.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ulanganpwpbsqlite.R;
import com.example.ulanganpwpbsqlite.database.DatabaseHelper;
import com.example.ulanganpwpbsqlite.models.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddNoteActivity extends AppCompatActivity
        implements View.OnClickListener {

    private EditText editTextTitle;
    private EditText editTextBody;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        this.setTitle("Tambah Note");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.editTextTitle = (EditText) findViewById(R.id.titleTxt);
        this.editTextBody = (EditText) findViewById(R.id.bodyTxt);
        this.saveBtn = (Button) findViewById(R.id.btnSubmit);
        this.saveBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveNote(DatabaseHelper db) {
        String title = this.editTextTitle.getText().toString();
        String body = this.editTextBody.getText().toString();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String createdAt = dateFormat.format(date);

        Note note = new Note();
        note.setTitle(title);
        note.setBody(body);
        note.setCreatedAt(createdAt);

        db.insertNote(note);

        Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(AddNoteActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        DatabaseHelper db = new DatabaseHelper(this);
        switch (v.getId()) {
            case R.id.btnSubmit:
                saveNote(db);
                break;
        }
    }
}