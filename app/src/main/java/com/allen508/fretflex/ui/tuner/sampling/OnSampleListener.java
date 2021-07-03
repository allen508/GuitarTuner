package com.allen508.fretflex.ui.tuner.sampling;

public interface OnSampleListener {
    boolean onSample(byte[] sample, double frequency);
}
