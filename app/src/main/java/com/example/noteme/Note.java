package com.example.noteme;
public class Note {
    private int id;
    private String title;
    private String subtitle;
    private String noteContent;
    private int color;

    // Constructor with all parameters (used when the id is known, e.g., for database queries)
    public Note(int id, String title, String subtitle, String noteContent, int color) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.noteContent = noteContent;
        this.color = color;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color){
        this.color = color;
    }
}
