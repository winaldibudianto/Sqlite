package com.example.ulanganpwpbsqlite.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ulanganpwpbsqlite.R;
import com.example.ulanganpwpbsqlite.adapters.NoteAdapter;
import com.example.ulanganpwpbsqlite.database.DatabaseHelper;
import com.example.ulanganpwpbsqlite.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements
        View.OnClickListener, NoteAdapter.onUserClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private List<Note> noteList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = this.getActivity();
        this.recyclerView = view.findViewById(R.id.noteListRecyclerView);
        this.layoutManager = new LinearLayoutManager(this.context);
        this.recyclerView.setLayoutManager(this.layoutManager);

        this.setupRecyclerView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddNote:
                Intent intent = new Intent(getActivity(), AddNoteActivity.class);
                startActivity(intent);
        }
    }

    @Override
    public void onUserClick(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Option");
        String[] options = {"Edit", "Delete"};

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        editData(note);
                        setupRecyclerView();
                        break;
                    case 1:
                        deleteNote(note);
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        this.setupRecyclerView();
    }

    private void setupRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(this.context);
        this.noteList = db.getAllNotes();

        NoteAdapter adapter = new NoteAdapter(this.context, this.noteList, this);
        this.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void editData(Note note) {
        Intent intent = new Intent(this.getContext(), EditNoteActivity.class);
        intent.putExtra("id", note.getId());
        intent.putExtra("title", note.getTitle());
        intent.putExtra("body", note.getBody());
        startActivity(intent);
    }

    private void deleteNote(Note note) {
        DatabaseHelper db = new DatabaseHelper(getContext());

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Delete");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton("Yes", (dialog, which) -> {
            db.deleteNote(note.getId());
            Toast.makeText(getContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
            setupRecyclerView();
        });

        alert.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });

        alert.show();
    }
}