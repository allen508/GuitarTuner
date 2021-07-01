package com.allen508.fretflex.sampler;

public class AnalysisResult {

    private byte[] bytes;
    private double frequency;

    public AnalysisResult(byte[] bytes, double frequency) {
        this.bytes = bytes;
        this.frequency = frequency;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public double getFrequency() {
        return frequency;
    }
}
