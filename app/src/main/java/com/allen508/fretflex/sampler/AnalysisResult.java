package com.allen508.fretflex.sampler;

import androidx.annotation.Nullable;

public class AnalysisResult {

    @Nullable private byte[] bytes;
    @Nullable
    private short[] sBytes;
    @Nullable private double frequency;
    @Nullable private int noteIndex;

    public AnalysisResult(@Nullable byte[] bytes, @Nullable short[] sBytes, @Nullable double frequency, @Nullable int noteIndex) {
        this.bytes = bytes;
        this.sBytes = sBytes;
        this.frequency = frequency;
        this.noteIndex = noteIndex;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public short[] getSBytes() {
        return this.sBytes;
    }

    public double getFrequency() { return this.frequency; }

    public int getNoteIndex() { return this.noteIndex;}
}
