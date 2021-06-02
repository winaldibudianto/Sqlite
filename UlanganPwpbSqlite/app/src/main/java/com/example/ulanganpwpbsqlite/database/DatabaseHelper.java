package com.example.ulanganpwpbsqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ulanganpwpbsqlite.models.Note;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "sticky_note";
    private static final String TABLE_NAME = "notes";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_BODY = "body";
    private static final String KEY_CREATED_AT = "createdAt";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="Create Table " + TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_TITLE + " TEXT, " +
                KEY_BODY + " TEXT, " +
                KEY_CREATED_AT + " TEXT" +  ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query=("drop table if exists " + TABLE_NAME);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, note.getTitle());
        contentValues.put(KEY_BODY, note.getBody());
        contentValues.put(KEY_CREATED_AT, note.getCreatedAt());

        db.insert(TABLE_NAME, null, contentValues);
    }

    public void updateNote(Note note) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, note.getTitle());
        contentValues.put(KEY_BODY, note.getBody());

        String whereClause = KEY_ID + "='" + note.getId() + "'";
        db.update(TABLE_NAME, contentValues, whereClause, null);
    }

    public List<Note> getAllNotes() {
        ArrayList<Note> noteArrayList = new ArrayList<Note>();

        SQLiteDatabase db = getReadableDatabase();
        String[] colums = {
                KEY_ID,
                KEY_TITLE,
                KEY_BODY,
                KEY_CREATED_AT
        };
        Cursor cursor = db.query(TABLE_NAME, colums, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Note note = new Note();
            note.setId(cursor.getInt(0));
            note.setTitle(cursor.getString(1));
            note.setBody(cursor.getString(2));
            note.setCreatedAt(cursor.getString(3));
            noteArrayList.add(note);
        }

        return noteArrayList;
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String where = KEY_ID + " = " + id;
        db.delete(TABLE_NAME, where, null);
    }
}
