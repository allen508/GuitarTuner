package com.allen508.fretflex.sampler.analysers;

import com.allen508.fretflex.sampler.AnalysisResult;
import com.allen508.fretflex.sampler.SampleAnalyser;

public class FrequencyIsolatorAnalyser implements SampleAnalyser {

    private static final int RECORDER_SAMPLERATE = 44100;

    @Override
    public AnalysisResult analyse(AnalysisResult result) {

        byte[] buffer = new byte[2 * 1200];
        int[] a = new int[buffer.length / 2];

        buffer = result.getBytes();

        for (int i = 0; i < buffer.length; i += 2) {
            // convert two bytes into single value
            int value = (short) ((buffer[i] & 0xFF) | ((buffer[i + 1] & 0xFF) << 8));
            a[i >> 1] = value;
        }

        double prevDiff = 0;
        double prevDx = 0;
        double maxDiff = 0;

        int sampleLen = 0;

        int len = a.length / 2;
        for (int i = 0; i < len; i++) {
            double diff = 0;
            for (int j = 0; j < len; j++) {
                diff += Math.abs(a[j] - a[i + j]);
            }

            double dx = prevDiff - diff;

            // change of sign in dx
            if (dx < 0 && prevDx > 0) {
                // only look for troughs that drop to less than 10% of peak
                if (diff < (0.50 * maxDiff)) {
                    if (sampleLen == 0) {
                        sampleLen = i - 1;
                    }
                }
            }

            prevDx = dx;
            prevDiff = diff;
            maxDiff = Math.max(diff, maxDiff);
        }

        double frequency = 0;
        if (sampleLen > 0) {
            frequency = (RECORDER_SAMPLERATE / sampleLen);
        }

        return new AnalysisResult(buffer, frequency, null);
    }

}
