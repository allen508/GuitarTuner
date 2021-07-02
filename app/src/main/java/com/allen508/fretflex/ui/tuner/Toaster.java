package com.allen508.fretflex.ui.tuner;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public class Toaster {

    private View view;
    private TunerViewModel viewModel;
    private LifecycleOwner owner;

    Toaster(View view, TunerViewModel viewModel, LifecycleOwner owner){

        this.view = view;
        this.viewModel = viewModel;
        this.owner = owner;
    }

    public void init() {

        final Observer<SampleResult> sampleObserver = new Observer<SampleResult>() {
            @Override
            public void onChanged(@Nullable final SampleResult sampleResult) {
                // Update the UI, in this case, a TextView.
                Toast.makeText(view.getContext(),String.valueOf(sampleResult.Frequency), Toast.LENGTH_LONG).show();
            }
        };

        viewModel.getFrequency().observe(owner, sampleObserver);

    }

}
