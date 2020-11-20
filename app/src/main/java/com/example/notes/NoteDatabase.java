package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NoteDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "note.db";
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_TABLE = "noteTable";
    //COLUMNS FOR TABLE
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";

    public NoteDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DATABASE_TABLE + "("+ KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_TITLE + " TEXT, " +
                KEY_CONTENT + " TEXT, " +
                KEY_DATE + " TEXT, " +
                KEY_TIME + " TEXT" +")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    public boolean insertNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, note.getTitle());
        cv.put(KEY_CONTENT, note.getContent());
        cv.put(KEY_DATE, note.getDate());
        cv.put(KEY_TIME, note.getTime());
        long insert = db.insert(DATABASE_TABLE, null, cv);
            if (insert == -1)
                return false;
            else
                return true;
    }

    public ArrayList<Note> getAllData() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Note> allNotes = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getString(0));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setDate(cursor.getString(3));
                note.setTime(cursor.getString(4));

                allNotes.add(note);
            } while (cursor.moveToNext());
        }
        return allNotes;

    }

    public boolean updateNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ID, note.getId());
        cv.put(KEY_TITLE, note.getTitle());
        cv.put(KEY_CONTENT, note.getContent());
        cv.put(KEY_DATE, note.getDate());
        cv.put(KEY_TIME, note.getTime());
        db.update(DATABASE_TABLE, cv, "id = ?", new String[]{String.valueOf(note.getId())});
        return true;
    }

    public int deleteData(Note note) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(DATABASE_TABLE, "id = ?", new String[]{String.valueOf(note.getId())});
    }

    public void deleteAllData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + DATABASE_TABLE);

    }
}
