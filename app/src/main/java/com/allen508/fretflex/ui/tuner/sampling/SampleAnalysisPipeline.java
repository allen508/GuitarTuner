package com.allen508.fretflex.ui.tuner.sampling;


import com.allen508.fretflex.sampler.AnalysisResult;
import com.allen508.fretflex.sampler.ReadCallbackHandler;

import com.allen508.fretflex.sampler.analysers.FrequencyDomainAnalyser;
import com.allen508.fretflex.sampler.analysers.FrequencyIsolatorAnalyser;
import com.allen508.fretflex.sampler.analysers.NoteFinder;

public class SampleAnalysisPipeline implements ReadCallbackHandler {

    private FrequencyDomainAnalyser frequencyDomainAnalyser;
    private FrequencyIsolatorAnalyser frequencyIsolatorAnalyser;
    private NoteFinder noteFinder;
    private ServiceResultSender serviceResultSender;

    public SampleAnalysisPipeline(FrequencyDomainAnalyser frequencyDomainAnalyser,
                                  FrequencyIsolatorAnalyser frequencyIsolatorAnalyser,
                                  NoteFinder noteFinder,
                                  ServiceResultSender serviceResultSender) {
        this.frequencyDomainAnalyser = frequencyDomainAnalyser;
        this.frequencyIsolatorAnalyser = frequencyIsolatorAnalyser;
        this.noteFinder = noteFinder;
        this.serviceResultSender = serviceResultSender;
    }

    @Override
    public void onRead(byte[] sample, short[] sSample) {

        // Fires when an audio sample has been collected

        // This function puts together a pipeline of actions
        // to run against the sample
        // 1. FrequencyDomainAnalyser (moves from the time domain to the frequency domain)
        // 2. FrequencyIsolator (isolates the dominant frequency)
        // 3. NoteFinder (finds the nearest note to the frequency given a scale)

        //Log.d("ONREAD2", Arrays.toString(sample));

        // Run the analysers
        AnalysisResult result = new AnalysisResult(sample, sSample,0.0, -1);
        //result = this.frequencyDomainAnalyser.analyse(result);
        result = this.frequencyIsolatorAnalyser.analyse(result);
        result = this.noteFinder.analyse(result);
        this.serviceResultSender.Send(result);

    }
}
