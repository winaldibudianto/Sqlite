package com.example.latihansqlitefaza.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.example.latihansqlitefaza.models.PersonBean;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "UserInfo";
    private static final String TABLE_NAME = "tbl_user";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create Table " + TABLE_NAME + "(" + KEY_NAME + " TEXT PRIMARY KEY, " + KEY_AGE + " INTEGER" + ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = ("DROP TABLE if EXISTS " + TABLE_NAME);
        db.execSQL(query);
        onCreate(db);
    }

    public void insert(PersonBean personBean) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, personBean.getName());
        contentValues.put(KEY_AGE, personBean.getAge());

        db.insert(TABLE_NAME, null, contentValues);
    }

    public List<PersonBean> selectUserData() {
        ArrayList<PersonBean> userList = new ArrayList<PersonBean>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_NAME, KEY_AGE};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while(cursor.moveToNext()) {
            String name = cursor.getString(0);
            int age = cursor.getInt(1);

            PersonBean personBean = new PersonBean();
            personBean.setName(name);
            personBean.setAge(age);
            userList.add(personBean);
        }

        return userList;
    }

    public void update(PersonBean personBean) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_AGE, personBean.getAge());
        String whereClause = KEY_NAME + "='" + personBean.getName() + "'";
        db.update(TABLE_NAME, contentValues, whereClause, null );
    }

    public void delete(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = KEY_NAME + "='" + name + "'";
        db.delete(TABLE_NAME, whereClause, null);
    }
}
