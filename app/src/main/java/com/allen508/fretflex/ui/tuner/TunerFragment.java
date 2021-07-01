package com.allen508.fretflex.ui.tuner;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allen508.fretflex.R;
import com.allen508.fretflex.sampler.Scale;
import com.allen508.fretflex.ui.tuner.mappers.ToSampler;
import com.allen508.fretflex.ui.tuner.sampling.SamplerIntentService;
import com.allen508.fretflex.ui.tuner.sampling.SamplerResultReceiver;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TunerFragment extends Fragment {

    TunerViewModel viewModel;
    private SamplerResultReceiver receiver;
    private Intent intent;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tuner_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(TunerViewModel.class);

        // TODO: Use the ViewModel

        Toaster toaster = new Toaster(view, viewModel, this);
        toaster.init();

        ScaleSpinner scaleSelector = new ScaleSpinner(view, viewModel);
        scaleSelector.init();

        // Long running background service

        ToSampler mapper = new ToSampler();
        Scale scale = mapper.FromViewModelScale(viewModel.getSelectedScale());

        this.receiver = new SamplerResultReceiver(new Handler());
        this.intent = new Intent(getActivity(), SamplerIntentService.class);
        intent.putExtra("receiver", this.receiver);

        getActivity().startService(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}


