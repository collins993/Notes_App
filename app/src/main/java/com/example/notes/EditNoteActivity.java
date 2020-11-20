package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class EditNoteActivity extends AppCompatActivity {
    EditText editTxtId, edit_Txt_Title, edit_Txt_Content;
    String id, title, content;
    NoteDatabase noteDatabase;
    Calendar calendar;
    String todaysDate;
    String currentTime;
    FloatingActionButton fab2;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.update_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteNote) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        editTxtId = findViewById(R.id.editTxtId);
        edit_Txt_Title = findViewById(R.id.edit_Txt_Title);
        edit_Txt_Content = findViewById(R.id.edit_Txt_Content);
        fab2 = findViewById(R.id.floatingActionButton2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note(editTxtId.getText().toString(),
                        edit_Txt_Title.getText().toString(),
                        edit_Txt_Content.getText().toString(),
                        todaysDate, currentTime);
                noteDatabase = new NoteDatabase(EditNoteActivity.this);
                boolean insert = noteDatabase.updateNote(note);

                if (insert)
                    Toast.makeText(EditNoteActivity.this, "Note Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(EditNoteActivity.this, "Note Not Updated", Toast.LENGTH_SHORT).show();

                onBackPressed();
            }
        });


        getAndSetIntent();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }

        calendar = Calendar.getInstance();
        todaysDate = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
        currentTime = pad(calendar.get(Calendar.HOUR)) + ":" + pad(calendar.get(Calendar.MINUTE));


    }

    private String pad(int i) {
        if (i < 10)
            return "0" + i;
        else
            return String.valueOf(i);
    }

    public void getAndSetIntent() {
        if (getIntent().hasExtra("id") &&
                getIntent().hasExtra("title") &&
                getIntent().hasExtra("content")) {

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            content = getIntent().getStringExtra("content");


            editTxtId.setText(id);
            editTxtId.setVisibility(View.GONE);
            edit_Txt_Title.setText(title);
            edit_Txt_Content.setText(content);
        }
    }

    public void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                noteDatabase = new NoteDatabase(EditNoteActivity.this);
                Note note = new Note(editTxtId.getText().toString(), edit_Txt_Title.getText().toString(),
                        edit_Txt_Content.getText().toString(), todaysDate, currentTime);
                int deletedBook = noteDatabase.deleteData(note);
                if (deletedBook != 0)
                    Toast.makeText(EditNoteActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(EditNoteActivity.this, "Note Not Deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }


}