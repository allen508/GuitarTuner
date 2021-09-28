package com.allen508.strumKit.data;

public class Note {
    private String name;
    private float frequency;
    private int keyNumber;
    private int octave;
    private int lookupIndex;
    private String accidental;
    private String specialDesignation;

    public Note(float frequency, String name, int octave, int keyNumber, int lookupIndex, String accidental, String specialDesignation) {
        this.frequency = frequency;
        this.name = name;
        this.octave = octave;
        this.keyNumber = keyNumber;
        this.lookupIndex = lookupIndex;
        this.accidental = accidental;
        this.specialDesignation = specialDesignation;
    }

    public int getLookupIndex() {
        return lookupIndex;
    }

    public float getFrequency() {
        return frequency;
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    public int getOctave() {
        return octave;
    }

    public String getAccidental() {
        return accidental;
    }

    public String getName() {
        return name;
    }

    public String getSpecialDesignation() {
        return specialDesignation;
    }
}