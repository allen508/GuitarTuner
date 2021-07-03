package com.allen508.fretflex.ui.tuner.sampling;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class SampleHandler extends Handler {

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        Log.d("CALL", "Hello");
    }
}
