package com.example.ulanganpwpbsqlite.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ulanganpwpbsqlite.R;
import com.example.ulanganpwpbsqlite.database.DatabaseHelper;
import com.example.ulanganpwpbsqlite.models.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditNoteActivity extends AppCompatActivity
        implements View.OnClickListener {

    private int id;
    private EditText txtTitle;
    private EditText txtBody;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Ubah");
        setContentView(R.layout.activity_edit_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.txtTitle = (EditText)findViewById(R.id.updateTitleTxt);
        this.txtBody = (EditText)findViewById(R.id.updateBodyTxt);
        this.btnUpdate = (Button)findViewById(R.id.btnUpdate);

        Intent intent = this.getIntent();
        this.id = intent.getIntExtra("id", 0);
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");

        this.txtTitle.setText(title);
        this.txtBody.setText(body);
        this.btnUpdate.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                finish();
                return true;
            case R.id.deleteBtn:
                deleteNote(this.id);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                updateNote();
                break;
        }
    }

    private void updateNote() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String createdAt = dateFormat.format(date);

        Note note = new Note();
        note.setId(this.id);
        note.setTitle(this.txtTitle.getText().toString());
        note.setBody(this.txtBody.getText().toString());
        note.setCreatedAt(createdAt);

        DatabaseHelper db = new DatabaseHelper(this);
        db.updateNote(note);

        Toast.makeText(this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(EditNoteActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    private void deleteNote(int id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton("Yes", (dialog, which) -> {
            DatabaseHelper db = new DatabaseHelper(this);
            db.deleteNote(id);

            Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(EditNoteActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        alert.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });

        alert.show();
    }
}