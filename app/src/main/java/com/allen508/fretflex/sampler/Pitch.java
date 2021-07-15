package com.allen508.fretflex.sampler;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class Pitch {
    private float frequency;
    private String letter;
    private int octave;
    private String accidental;

    public Pitch(float frequncy) {
        this.frequency = frequncy;
    }

    public Pitch(float frequncy, String letter, int octave, String accidental) {
        this.frequency = frequncy;
        this.letter = letter;
        this.octave = octave;
        this.accidental = accidental;
    }


    public String getLetter() {
        return letter;
    }

    public int getOctave() {
        return octave;
    }

    public String getAccidental() {
        return accidental;
    }

    public float getFrequency() {
        return frequency;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return frequency + " " + letter + " " + octave + " " + accidental;
    }
}
