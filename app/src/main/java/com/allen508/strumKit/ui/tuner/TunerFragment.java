package com.allen508.strumKit.ui.tuner;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen508.strumKit.R;
import com.allen508.strumKit.databinding.TunerFragmentBinding;
import com.allen508.strumKit.sampler.AudioSampler;
import com.allen508.strumKit.sampler.FrequencyAnalyser;
import com.google.android.gms.ads.AdView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TunerFragment extends Fragment {


    private AudioSampler sampler;
    private FrequencyAnalyser analyser;
    //Layout components
    private TunerSurface tunerSurface;
    private AdView adView;


    private TunerFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        binding = TunerFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //View view = inflater.inflate(R.layout.tuner_fragment, container, false);
        view.setBackgroundColor(Color.rgb(0, 0, 0));

        ImageView settingstView = view.findViewById(R.id.settings_image);

        settingstView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    NavOptions.Builder builder = new NavOptions.Builder();
                    builder.setPopUpTo(R.id.tuningsFragment, false);
                    //builder.setEnterAnim(R.anim.slide_left);
                    //builder.setEnterAnim(R.anim.slide_right);
                    //builder.setPopEnterAnim(R.anim.slide_left);
                    //builder.setPopExitAnim(R.anim.slide_right);

                    NavDirections action = TunerFragmentDirections.actionTunerFragmentToTuningsFragment();
                    Navigation.findNavController(view).navigate(action,builder.build());

                }

                return false;
            }
        });


        //adView = view.findViewById(R.id.ad_view);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //try{
        //    adView.loadAd(adRequest);
        //}
        //catch(Error error) {
        //    Log.d("Error", error.getMessage());
        //}

        return view;
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


        String tuningName = "STANDARD";

        if(getArguments() != null) {
            tuningName = getArguments().getString("tuningName");
        }

        TextView tuningText = view.findViewById(R.id.tuner_text1);
        tuningText.setText(tuningName);
        tunerSurface = view.findViewById(R.id.tuner_surface);
        tunerSurface.setTuning(tuningName);
        analyser = new FrequencyAnalyser(tunerSurface);

    }


    @Override
    public void onResume() {
        super.onResume();
        sampler = new AudioSampler(analyser);
        sampler.start();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}


