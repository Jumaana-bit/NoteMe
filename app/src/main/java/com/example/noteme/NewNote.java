package com.example.noteme;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NewNote extends AppCompatActivity {

    // creating variables for our edittext, button and dbhandler
    private EditText note;
    private EditText title;
    private EditText subtitle;
    private int pickedColor = Color.WHITE;
    private Button saveNote, pickColorButton;
    private DatabaseHelper dbHandler;
    private LinearLayout noteContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // initializing all our variables.
        note = findViewById(R.id.Note);
        title = findViewById(R.id.Title);
        subtitle = findViewById(R.id.Subtitle);
        pickColorButton = findViewById(R.id.pickColorButton);
        saveNote = findViewById(R.id.button);
        noteContainer = findViewById(R.id.noteContainer);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DatabaseHelper(NewNote.this);

        // Set up color picker
        pickColorButton.setOnClickListener(v -> openColorPickerDialog());

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // below line is to get data from all edit text fields.
                String notes = note.getText().toString();
                String noteTitle = title.getText().toString();
                String noteSubtitile = subtitle.getText().toString();

                // validating if the text fields are empty or not.
                if (notes.isEmpty() || noteTitle.isEmpty()) {
                    Toast.makeText(NewNote.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                dbHandler.addNewNote(noteTitle, noteSubtitile, notes, pickedColor);

                // after adding the data we are displaying a toast message.
                Toast.makeText(NewNote.this, "Note has been added.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void launchHomeNote(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    // Function to open the color picker
    private void openColorPickerDialog() {
        final String[] colorNames = {"Red", "Blue", "Green", "Yellow", "Purple", "Orange", "Black", "White"};
        final int[] colors = {
                ContextCompat.getColor(this, R.color.colorRed),
                ContextCompat.getColor(this, R.color.colorBlue),
                ContextCompat.getColor(this, R.color.colorGreen),
                ContextCompat.getColor(this, R.color.colorYellow),
                ContextCompat.getColor(this, R.color.colorPurple),
                ContextCompat.getColor(this, R.color.colorOrange),
                ContextCompat.getColor(this, R.color.black),
                ContextCompat.getColor(this, R.color.white)
        };


        // Create a dialog with color choices
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a color");

        builder.setItems(colorNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pickedColor = colors[which]; // Update the selected color
                pickColorButton.setBackgroundColor(pickedColor); // Update button background to show selected color
            }
        });

        builder.show();
    }

   /* // Function to show the note in the UI
    private void showNoteInView(String noteTitle, String subtitle, String notes, int color) {
        // Inflate the note_item layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View noteView = inflater.inflate(R.layout.note_item, noteContainer, false);

        // Get references to TextViews in note_item layout
        TextView titleView = noteView.findViewById(R.id.note_title);
        TextView subtitleView = noteView.findViewById(R.id.note_subtitle);
        TextView contentView = noteView.findViewById(R.id.note_content);


        // Set the title and content
        titleView.setText(noteTitle);
        subtitleView.setText(subtitle);
        contentView.setText(notes);

        // Set the background color of the note
        noteView.setBackgroundColor(color);

        // Add the inflated note to the container
        noteContainer.addView(noteView);
    }*/
}
