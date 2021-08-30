package com.allen508.fretflex.ui.tuner;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Spinner;

import com.allen508.fretflex.R;
import com.allen508.fretflex.data.NoteRepository;
import com.allen508.fretflex.sampler.AudioSampler;
import com.allen508.fretflex.sampler.FrequencyAnalyser;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TunerFragment extends Fragment {

    private AudioSampler sampler;
    private FrequencyAnalyser analyser;

    //Layout components
    private TunerSurface tunerSurface;


    private AdView adView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.tuner_fragment, container, false);
        view.setBackgroundColor(Color.rgb(0, 0, 0));

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        adView = view.findViewById(R.id.ad_view);

        // Create an ad request.
        AdRequest adRequest = new AdRequest.Builder().build();

        try{
            // Start loading the ad in the background.
            adView.loadAd(adRequest);

        }
        catch(Error error) {
            Log.d("Error", error.getMessage());
        }

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

        tunerSurface = view.findViewById(R.id.tuner_surface);
        analyser = new FrequencyAnalyser(tunerSurface);

        NoteRepository repository = new NoteRepository();
        String[] tuningNames = repository.getTuningNames();

//        Button btn =(Button)view.findViewById(R.id.show_tuning_menu);
 //       btn.setOnClickListener(new View.OnClickListener() {
 //           @Override
 //           public void onClick(View v) {
 //               PopupMenu popup = new PopupMenu(getActivity(), v);
 //               popup.inflate(R.menu.popup_menu);
 //               for (String tuningName : tuningNames) {
 //                   popup.getMenu().add(tuningName);
 //               }
 //               popup.show();
 //           }
 //       });
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


