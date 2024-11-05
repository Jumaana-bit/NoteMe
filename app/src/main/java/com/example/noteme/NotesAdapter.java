package com.example.noteme;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private Context context;
    private List<Note> noteList;

    public NotesAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setFilteredList(List<Note> filteredList) {
        this.noteList = filteredList;
        notifyDataSetChanged();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle, noteSubtitle, noteContent;
        ImageButton deleteNote;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_title);
            noteContent = itemView.findViewById(R.id.note_content);
            noteSubtitle = itemView.findViewById(R.id.note_subtitle);
            deleteNote = itemView.findViewById(R.id.delete_note); // Bind the delete button
        }
    }


    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.noteSubtitle.setText(note.getSubtitle());
        holder.noteContent.setText(note.getNoteContent());

        // Set background color of the note tile
        holder.itemView.setBackgroundColor(note.getColor());

        // Log the note details
        Log.d("NoteAdapter", "Position: " + position + " - Title: " + note.getTitle());
        Log.d("NoteAdapter", "Position: " + position + " - Title Height: " + holder.noteTitle.getHeight());

        // Handle delete note
        holder.deleteNote.setOnClickListener(v -> {
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            Log.d("DEBUG", "Deleting note with id: " + note.getId()); // Log the ID
            dbHelper.deleteNote(note.getId()); // Delete the note from the database
            noteList.remove(position);         // Remove the note from the list
            notifyItemRemoved(position);       // Notify RecyclerView to update the UI
            notifyItemRangeChanged(position, noteList.size()); // Update the range of notes displayed
        });

    }

}
