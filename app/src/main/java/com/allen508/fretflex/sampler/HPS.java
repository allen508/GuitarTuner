package com.allen508.fretflex.sampler;

import android.util.Log;

public class HPS {
    private static final String LOGTAG = "HPS";

    public void hps(float[] mag, float[] hps, int order) {
        if(mag.length != hps.length) {
            Log.e(LOGTAG, "calcHarmonicProductSpectrum: mag[] and hps[] have to be of the same length!");
            throw new IllegalArgumentException("mag[] and hps[] have to be of the same length");
        }

        // initialize the hps array
        int hpsLength = mag.length / (order+1);
        for (int i = 0; i < hps.length; i++) {
            if(i < hpsLength)
                hps[i] = mag[i];
            else
                hps[i] = Float.NEGATIVE_INFINITY;
        }

        // do every harmonic in a big loop:
        for (int harmonic = 1; harmonic <= order; harmonic++) {
            int downsamplingFactor = harmonic + 1;
            for (int index = 0; index < hpsLength; index++) {
                // Calculate the average (downsampling):
                float avg = 0;
                for (int i = 0; i < downsamplingFactor; i++) {
                    avg += mag[index*downsamplingFactor + i];
                }
                hps[index] += avg / downsamplingFactor;
            }
        }
    }


}
