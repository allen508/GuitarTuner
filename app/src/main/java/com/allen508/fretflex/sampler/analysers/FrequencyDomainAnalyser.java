package com.allen508.fretflex.sampler.analysers;

import android.util.Log;

import com.allen508.fretflex.sampler.AnalysisResult;
import com.allen508.fretflex.sampler.SampleAnalyser;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;


public class FrequencyDomainAnalyser implements SampleAnalyser {

    public @Nullable
    AnalysisResult analyse(AnalysisResult result) {

        int freq = run(result.getSBytes());

        AnalysisResult result1 = new AnalysisResult(result.getBytes(), result.getSBytes(), freq, result.getNoteIndex());

        return result1;
    }

    private int run(short[] sample) {

        double magnitude = 0;
        int detect_freq = 0;

        double max_magnitude = 200;

        int bufferSize = sample.length-1;
        double[] dbSample = new double[bufferSize];

        for (int j = 0; j < bufferSize; j++) {
            dbSample[j] = (double) sample[j];
        }

        int freq = 75;
        while (freq <= 95) {
            Goertzel g = new Goertzel(44100, freq, bufferSize);
            g.initGoertzel();
            for (int i = 0; i < bufferSize; i++) {
                g.processSample(dbSample[i]);
            }
            magnitude = Math.sqrt(g.getMagnitudeSquared());
            if (magnitude > max_magnitude) {
                max_magnitude = magnitude;
                detect_freq = freq;
            }
            g.resetGoertzel();

            freq += 1;
        }

        Log.d("GOERTZEL", String.valueOf(magnitude) + " - " + String.valueOf(detect_freq));
        return detect_freq;
    }




}



