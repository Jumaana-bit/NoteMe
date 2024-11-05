package com.example.noteme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NoteME";
    // below int is our database version
    private static final int DB_VERSION = 2;
    public static final String TABLE = "Notes";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Title";
    public static final String COL = "Subtitle";
    public static final String COL_3 = "Description";
    public static final String COL_4 = "Color";


    // creating a constructor for our database handler.
    public DatabaseHelper (@Nullable Context context){
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "create table " + TABLE + "(" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT NOT NULL, " + COL + " TEXT, "+ COL_3 + " TEXT, " + COL_4 + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    // this method is use to add new email to our sqlite database.
    public long addNewNote(String noteTitle, String noteSubtitle, String description, int color) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(COL_2, noteTitle);      // Title
        values.put(COL, noteSubtitle);     // Subtitle
        values.put(COL_3, description);    // Description
        values.put(COL_4, color);          // Color


        // after adding all values we are passing
        // content values to our table.
        long id = db.insert(TABLE, null, values);
        // This returns the inserted row ID

        // at last we are closing our
        // database after adding database.
        db.close();

        return id;
    }

    public List<Note> getAllNotes() {
        List<Note> notesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_1));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COL_2));
                String subtitle = cursor.getString(cursor.getColumnIndexOrThrow(COL));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(COL_3));
                int color = cursor.getInt(cursor.getColumnIndexOrThrow(COL_4));
                notesList.add(new Note(id, title, subtitle, content, color));
            } while (cursor.moveToNext());
        }

        cursor.close();
        Log.d("DEBUG", "Notes retrieved: " + notesList.size()); // Log to check the data
        return notesList;
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE + " ADD COLUMN " + COL + " TEXT");
        }
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("notes", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }


}
