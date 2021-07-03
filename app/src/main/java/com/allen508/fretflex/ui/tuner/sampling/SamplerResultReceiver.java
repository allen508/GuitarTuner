package com.allen508.fretflex.ui.tuner.sampling;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

public class SamplerResultReceiver extends ResultReceiver {

    private OnSampleListener listener;

    public SamplerResultReceiver(Handler handler, OnSampleListener listener) {
        super(handler);
        this.listener = listener;
    }


    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);

        double frequency = resultData.getDouble("sample_frequency");
        byte[] bytes = resultData.getByteArray("sample_bytes");
        String nearestNote = resultData.getString("sample_nearest_note");

        this.listener.onSample(bytes, frequency);

    }

}