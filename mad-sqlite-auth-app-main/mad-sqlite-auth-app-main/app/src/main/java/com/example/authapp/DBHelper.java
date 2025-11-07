package com.example.authapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AuthDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USERS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userid TEXT UNIQUE, " +
                "password TEXT)";
        db.execSQL(createTable);

        // Insert default user (for testing)
        ContentValues values = new ContentValues();
        values.put("userid", "admin");
        values.put("password", "1234");
        db.insert(TABLE_USERS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Insert new user
    public boolean insertUser(String userid, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userid", userid);
        values.put("password", password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // Check login credentials
    public boolean checkUser(String userid, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE userid=? AND password=?",
                new String[]{userid, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}