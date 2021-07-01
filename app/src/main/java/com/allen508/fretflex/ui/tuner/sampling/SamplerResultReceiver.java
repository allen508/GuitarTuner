package com.allen508.fretflex.ui.tuner.sampling;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import java.util.Arrays;

public class SamplerResultReceiver extends ResultReceiver {

    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    public SamplerResultReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);

        //TODO: Link into view model

        Log.d("RECEIVER4", String.valueOf(resultData.getDouble("sample_frequency")));
        //Log.d("RECEIVER5", Arrays.toString(resultData.getByteArray("sample_bytes")));

    }
}