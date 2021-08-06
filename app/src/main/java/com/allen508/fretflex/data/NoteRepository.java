package com.allen508.fretflex.data;

import java.util.List;

public class NoteRepository {

    private InMemoryNoteData data;

    public NoteRepository(){
        data = new InMemoryNoteData();
        data.initData();
    }

    public Note getPitchStandard(){
        return data.getPitchStandard();
    }

    public Note getC0(){
        return data.getC0();
    }

    public List<Note> getAllNotes(){
        return data.getAllNotes();
    }

    public List<Note> getStandardTuning(){
        return data.getStandardTuning();
    }

    public String[] getTuningNames() { return data.getTuningNames(); }

    public List<Note> getTuning(String name){ return data.getTuning(name); }
}
