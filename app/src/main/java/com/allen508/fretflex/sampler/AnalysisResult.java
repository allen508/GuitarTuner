package com.allen508.fretflex.sampler;

import androidx.annotation.Nullable;

public class AnalysisResult {

    @Nullable private byte[] bytes;
    @Nullable private double frequency;
    @Nullable private String nearestNote;

    public AnalysisResult(@Nullable byte[] bytes, @Nullable double frequency, @Nullable String nearestNote) {
        this.bytes = bytes;
        this.frequency = frequency;
        this.nearestNote = nearestNote;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public double getFrequency() { return this.frequency; }

    public String getNearestNote() { return this.nearestNote;}
}
