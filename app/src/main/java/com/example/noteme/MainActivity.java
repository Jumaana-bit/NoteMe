package com.example.noteme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;
    private List<Note> noteList = new ArrayList<>(); // Holds all notes from the database
    private DatabaseHelper dbHandler;
    private EditText searchView;
    private TextView noNotesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        noNotesTextView = findViewById(R.id.noNotes);

        dbHandler = new DatabaseHelper(MainActivity.this);

        // Retrieve notes from database
        noteList = dbHandler.getAllNotes(); // You need to implement this method in DatabaseHelper

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesAdapter = new NotesAdapter(this, noteList);
        recyclerView.setAdapter(notesAdapter);

        // Show or hide the no notes TextView
        updateNoNotesView();

        // Set up search/filter functionality
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void updateNoNotesView() {
        if (noteList.isEmpty()) {
            noNotesTextView.setVisibility(View.VISIBLE); // Show the TextView
            recyclerView.setVisibility(View.GONE); // Hide RecyclerView
        } else {
            noNotesTextView.setVisibility(View.GONE); // Hide the TextView
            recyclerView.setVisibility(View.VISIBLE); // Show RecyclerView
        }
    }

    // Method to filter notes by title
    private void filter(String text) {
        List<Note> filteredList = new ArrayList<>();
        if (text.isEmpty()) {
            filteredList.addAll(noteList); // Return the full list if the search is empty
        } else {
            for (Note note : noteList) {
                if (note.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(note);
                }
            }
        }

        notesAdapter.setFilteredList(filteredList);
        updateNoNotesView(); // Check if there are notes after filtering
    }


    public void launchNewNote(View v) {
        Intent i = new Intent(this, NewNote.class);
        startActivity(i);
    }
}