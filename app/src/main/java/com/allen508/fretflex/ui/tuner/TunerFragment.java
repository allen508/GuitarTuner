package com.allen508.fretflex.ui.tuner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.allen508.fretflex.R;
import com.allen508.fretflex.data.NoteRepository;
import com.allen508.fretflex.sampler.AudioSampler;
import com.allen508.fretflex.sampler.FrequencyAnalyser;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TunerFragment extends Fragment {

    private AudioSampler sampler;
    private FrequencyAnalyser analyser;

    //Layout components
    private TunerSurface tunerSurface;

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


        tunerSurface = view.findViewById(R.id.tuner_surface);
        analyser = new FrequencyAnalyser(tunerSurface);

        NoteRepository repository = new NoteRepository();
        String[] arraySpinner = repository.getTuningNames();

        Spinner s = (Spinner) getActivity().findViewById(R.id.spinner1);


        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                tunerSurface.setTuning(arraySpinner[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        sampler = new AudioSampler(analyser);
        sampler.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}


