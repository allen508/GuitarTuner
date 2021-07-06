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
import com.allen508.fretflex.ui.tuner.sampling.SamplerIntentService;
import com.allen508.fretflex.ui.tuner.sampling.SamplerResultReceiver;
import com.allen508.fretflex.ui.tuner.tuning.TunerView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TunerFragment extends Fragment {

    private TunerViewModel viewModel;
    private SamplerResultReceiver receiver;
    private Intent samplerIntent;

    //Layout components
    private TunerView tunerView;
    private Toaster toaster;
    private ScaleSpinner scaleSelector;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tuner_fragment, container, false);
    }


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.viewModel = new ViewModelProvider(this).get(TunerViewModel.class);

        this.toaster = new Toaster(view, viewModel, this);
        this.toaster.init();

        this.scaleSelector = new ScaleSpinner(view, viewModel);
        this.scaleSelector.init();

        this.tunerView = view.findViewById(R.id.tuner_view);
        this.tunerView.startGameView();

        //SAMPLER SERVICE

        this.samplerIntent = new Intent(getActivity(), SamplerIntentService.class);
        this.receiver = new SamplerResultReceiver(new Handler(), this.tunerView);
        this.samplerIntent.putExtra("receiver", this.receiver);
        this.samplerIntent.putExtra("scaleIndex", 1);

        getActivity().startService(this.samplerIntent);

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().stopService(this.samplerIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().startService(this.samplerIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().stopService(this.samplerIntent);
    }
}


