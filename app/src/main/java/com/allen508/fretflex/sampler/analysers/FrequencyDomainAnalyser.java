package com.allen508.fretflex.sampler.analysers;

import android.util.Log;

import com.allen508.fretflex.sampler.AnalysisResult;
import com.allen508.fretflex.sampler.SampleAnalyser;

import java.util.Arrays;

import javax.annotation.Nullable;

public class FrequencyDomainAnalyser implements SampleAnalyser {

    public @Nullable
    AnalysisResult analyse(AnalysisResult result) {

        //Log.d("ANALYSIS3", Arrays.toString(result.getBytes()));

        return new AnalysisResult(result.getBytes(), result.getFrequency(), result.getNearestNote());
    }

}
