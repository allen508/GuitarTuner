package com.allen508.fretflex.ui.tuner.sampling;


import android.util.Log;

import androidx.annotation.Nullable;

import com.allen508.fretflex.sampler.AnalysisResult;
import com.allen508.fretflex.sampler.ReadCallbackHandler;

import com.allen508.fretflex.sampler.analysers.FrequencyDomainAnalyser;
import com.allen508.fretflex.sampler.analysers.FrequencyIsolatorAnalyser;

import java.util.Arrays;

public class SampleAnalysisPipeline implements ReadCallbackHandler {

    private FrequencyDomainAnalyser frequencyDomainAnalyser;
    private FrequencyIsolatorAnalyser frequencyIsolatorAnalyser;
    private ServiceResultSender serviceResultSender;

    public SampleAnalysisPipeline(FrequencyDomainAnalyser frequencyDomainAnalyser,
                                  FrequencyIsolatorAnalyser frequencyIsolatorAnalyser,
                                  ServiceResultSender serviceResultSender) {
        this.frequencyDomainAnalyser = frequencyDomainAnalyser;
        this.frequencyIsolatorAnalyser = frequencyIsolatorAnalyser;
        this.serviceResultSender = serviceResultSender;
    }

    @Override
    public void onRead(byte[] sample) {

        // Fires when an audio sample has been collected

        // This function puts together a pipeline of actions
        // to run against the sample
        // 1. FrequencyDomainAnalyser (moves from the time domain to the frequency domain)
        // 2. FrequencyIsolator (isolates the dominant frequency)
        // 3. NoteFinder (finds the nearest note to the frequency given a scale)

        //Log.d("ONREAD2", Arrays.toString(sample));

        // Run the analysers
        AnalysisResult result = null;
        result = this.frequencyDomainAnalyser.analyse(sample);
        result = this.frequencyIsolatorAnalyser.analyse(sample);
        this.serviceResultSender.Send(result);

    }
}
