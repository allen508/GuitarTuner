package com.allen508.fretflex.ui.tuner.sampling;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.os.ResultReceiver;

import androidx.annotation.Nullable;

import com.allen508.fretflex.sampler.analysers.FrequencyDomainAnalyser;
import com.allen508.fretflex.sampler.SampleRecorder;
import com.allen508.fretflex.sampler.analysers.FrequencyIsolatorAnalyser;

public class SamplerIntentService extends IntentService {

    private SampleRecorder recorder;
    private ResultReceiver receiver;
   // private NoteFinder noteFinder;

    public SamplerIntentService() {
        super("SamplerIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public ComponentName startService(Intent service) {
        return super.startService(service);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        FrequencyDomainAnalyser frequencyDomainAnalyser = new FrequencyDomainAnalyser();
        FrequencyIsolatorAnalyser frequencyIsolatorAnalyser = new FrequencyIsolatorAnalyser();

        this.receiver = (ResultReceiver)intent.getParcelableExtra("receiver");
        ServiceResultSender serviceResultSender = new ServiceResultSender(this.receiver);

        SampleAnalysisPipeline pipeline = new SampleAnalysisPipeline(frequencyDomainAnalyser, frequencyIsolatorAnalyser, serviceResultSender);
        this.recorder = new SampleRecorder(pipeline);



        this.recorder.Start();

    }

    @Override
    public void onDestroy() {
        this.recorder.destroy();
        super.onDestroy();
    }

}

