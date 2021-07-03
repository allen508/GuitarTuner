package com.allen508.fretflex.ui.tuner.sampling;

import android.os.Bundle;
import android.os.ResultReceiver;

import com.allen508.fretflex.sampler.AnalysisResult;

public class ServiceResultSender {

    private ResultReceiver receiver;

    public static final int SAMPLE_ANALYSED = 8344;

    public ServiceResultSender(ResultReceiver receiver)
    {
        this.receiver = receiver;
    }

    public void Send(AnalysisResult result){
        Bundle resultData = new Bundle();
        resultData.putByteArray("sample_bytes", result.getBytes());
        resultData.putDouble("sample_frequency", result.getFrequency());
        resultData.putString("sample_nearest_note", result.getNearestNote());
        receiver.send(SAMPLE_ANALYSED, resultData);
    }


}
