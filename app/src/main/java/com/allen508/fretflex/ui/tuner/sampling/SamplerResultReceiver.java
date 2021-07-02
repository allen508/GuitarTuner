package com.allen508.fretflex.ui.tuner.sampling;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

public class SamplerResultReceiver extends ResultReceiver {

    private double sampleFrequency;
    private byte[] sampleBytes;

    public SamplerResultReceiver(Handler handler) {
        super(handler);
    }

    public double getSampleFrequency() {
        return sampleFrequency;
    }

    public byte[] getSampleBytes() {
        return sampleBytes;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);

        //TODO: Link into view model

        this.sampleFrequency = resultData.getDouble("sample_frequency");
        this.sampleBytes = resultData.getByteArray("sample_bytes");

        Log.d("RECEIVER4", String.valueOf(resultData.getDouble("sample_frequency")));
        //Log.d("RECEIVER5", Arrays.toString(resultData.getByteArray("sample_bytes")));

    }

}