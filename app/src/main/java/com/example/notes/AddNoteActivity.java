package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity {
    Context context;
    EditText noteTitle, noteContext;
    NoteDatabase noteDatabase;
    public static Calendar calendar;
    public static String todaysDate;
    public static String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        noteTitle = findViewById(R.id.edit_text_title);
        noteContext = findViewById(R.id.edit_text_content);
        noteDatabase = new NoteDatabase(this);
        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //get current date and time
        calendar = Calendar.getInstance();
        todaysDate = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
        currentTime =  pad(calendar.get(Calendar.HOUR)) + ":" +  pad(calendar.get(Calendar.MINUTE));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.saveNote) {
            Note note = new Note(noteTitle.getText().toString(), noteContext.getText().toString(), todaysDate, currentTime);
            boolean insert = noteDatabase.insertNote(note);
            if (insert)
                    Toast.makeText(AddNoteActivity.this, "Note Inserted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(AddNoteActivity.this, "Note Not Inserted", Toast.LENGTH_SHORT).show();

            goToMain();
        }
        return super.onOptionsItemSelected(item);

    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private String pad(int i) {
        if (i < 10)
            return "0" + i;
        else
            return String.valueOf(i);
    }

}