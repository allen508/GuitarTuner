package com.allen508.fretflex.ui.tuner;

import java.util.ArrayList;

public class Scale {
    private String name;
    private ArrayList<String> notes;

    public Scale(String name, String note1, String note2, String note3, String note4, String note5, String note6) {
        this.name = name;
        notes = new ArrayList<String>();
        notes.add(note1);
        notes.add(note2);
        notes.add(note3);
        notes.add(note4);
        notes.add(note5);
        notes.add(note6);
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getNotes() {
        return this.notes;
    }

}
