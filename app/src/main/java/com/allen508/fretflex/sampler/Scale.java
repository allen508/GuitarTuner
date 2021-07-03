package com.allen508.fretflex.sampler;

import android.os.Parcelable;

import java.util.ArrayList;

public class Scale {

    private String name;
    private ArrayList<String> notes;

    public Scale() {

    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public ArrayList<String> getNotes() {
        return this.notes;
    }

    public void setNotes(ArrayList<String> value) {
        this.notes = value;
    }

}
