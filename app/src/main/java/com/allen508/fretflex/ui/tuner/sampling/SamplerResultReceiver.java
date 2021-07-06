package com.allen508.fretflex.ui.tuner.sampling;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

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
        int noteIndex = resultData.getInt("sample_note_index");

        this.listener.onSample(bytes, frequency, noteIndex);

    }

}