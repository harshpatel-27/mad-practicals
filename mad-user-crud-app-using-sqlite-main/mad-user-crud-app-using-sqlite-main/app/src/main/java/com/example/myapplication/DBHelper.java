package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserDB.db";
    public static final String TABLE_NAME = "users";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_ADDRESS = "address";
    public static final String COL_CONTACT = "contact";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_ADDRESS + " TEXT, " +
                COL_CONTACT + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertUser(String name, String address, String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_ADDRESS, address);
        cv.put(COL_CONTACT, contact);
        long result = db.insert(TABLE_NAME, null, cv);
        return result != -1;
    }

    public boolean updateUser(String id, String name, String address, String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_ADDRESS, address);
        cv.put(COL_CONTACT, contact);
        int result = db.update(TABLE_NAME, cv, COL_ID + "=?", new String[]{id});
        return result > 0;
    }

    public boolean deleteUser(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ID + "=?", new String[]{id});
        return result > 0;
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}