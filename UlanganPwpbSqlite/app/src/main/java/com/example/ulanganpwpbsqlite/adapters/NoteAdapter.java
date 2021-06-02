package com.example.ulanganpwpbsqlite.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulanganpwpbsqlite.R;
import com.example.ulanganpwpbsqlite.activities.AddNoteActivity;
import com.example.ulanganpwpbsqlite.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private Context context;
    private onUserClickListener listener;
    private List<Note> noteList;

    public NoteAdapter(Context context, List<Note> noteList, onUserClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        NoteViewHolder noteViewHolder = new NoteViewHolder(view);

        return noteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        final Note current = this.noteList.get(position);
        holder.noteDate.setText(current.getCreatedAt());
        holder.noteTitle.setText(current.getTitle());
        holder.noteBody.setText(current.getBody());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserClick(current);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.noteList.size();
    }

    public interface onUserClickListener {
        void onUserClick(Note note);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView noteDate;
        TextView noteTitle;
        TextView noteBody;

        public NoteViewHolder(@NonNull View view) {
            super(view);
            this.linearLayout = view.findViewById(R.id.noteItem);
            this.noteDate = view.findViewById(R.id.noteDate);
            this.noteTitle = view.findViewById(R.id.noteTitle);
            this.noteBody = view.findViewById(R.id.noteBody);
        }
    }

}
