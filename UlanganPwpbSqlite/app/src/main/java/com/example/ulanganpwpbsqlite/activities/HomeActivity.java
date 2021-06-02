package com.example.ulanganpwpbsqlite.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.ulanganpwpbsqlite.R;
import com.example.ulanganpwpbsqlite.adapters.NoteAdapter;
import com.example.ulanganpwpbsqlite.database.DatabaseHelper;
import com.example.ulanganpwpbsqlite.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements View.OnClickListener {

    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.button = (FloatingActionButton)findViewById(R.id.fabAddNote);

        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framercontainer, fragment);
        fragmentTransaction.commit();

        this.button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddNote:
                createNote();
        }
    }

    private void createNote() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }
}