package com.allen508.fretflex.sampler.analysers;

import com.allen508.fretflex.sampler.AnalysisResult;
import com.allen508.fretflex.sampler.SampleAnalyser;

public class NoteFinder implements SampleAnalyser {

    //private static final double[] FREQUENCIES = { 174.61, 164.81, 155.56, 146.83, 138.59, 130.81, 123.47, 116.54, 110.00, 103.83, 98.00, 92.50, 87.31, 82.41, 77.78};
    //private static final String[] NAME        = { "F",    "E",    "D#",   "D",    "C#",   "C",    "B",    "A#",   "A",    "G#",   "G",   "F#",  "F",   "E",   "D#"};

    private static final double[] FREQUENCIES = { 329.63, 246.94, 196.00, 146.83, 110.00, 82.41};
    private static final String[] NAME        = { "E",    "B",    "G",   "D",    "A",    "E"};

    private int scaleIndex;

    public NoteFinder(int scaleIndex) {
        this.scaleIndex = scaleIndex;
    }

    @Override
    public AnalysisResult analyse(AnalysisResult result) {

        int note = closestNote(result.getFrequency());

        return new AnalysisResult(result.getBytes(), result.getFrequency(), NAME[note]);

    }

    private static double normaliseFreq(double hz) {
        if(hz == 0.0){
            return hz;
        }

        // get hz into a standard range to make things easier to deal with
        while ( hz < 82.41 ) {
            hz = 2*hz;
        }
        while ( hz > 164.81 ) {
            hz = 0.5*hz;
        }
        return hz;
    }

    private static int closestNote(double hz) {
        hz = normaliseFreq(hz);

        double minDist = Double.MAX_VALUE;
        int minFreq = -1;
        for ( int i = 0; i < FREQUENCIES.length; i++ ) {
            double dist = Math.abs(FREQUENCIES[i]-hz);
            if ( dist < minDist ) {
                minDist=dist;
                minFreq=i;
            }
        }

        return minFreq;
    }

}
