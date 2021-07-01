package com.allen508.fretflex.sampler.analysers;

import android.util.Log;

import com.allen508.fretflex.sampler.AnalysisResult;
import com.allen508.fretflex.sampler.SampleAnalyser;

import java.util.Arrays;

import javax.annotation.Nullable;

public class FrequencyDomainAnalyser implements SampleAnalyser {

    public @Nullable
    AnalysisResult analyse(byte[] signal) {

        AnalysisResult result = new AnalysisResult(signal, 0.0);

        //Log.d("ANALYSIS3", Arrays.toString(result.getBytes()));

        return result;
    }

}
