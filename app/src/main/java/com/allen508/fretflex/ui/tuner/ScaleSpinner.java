package com.allen508.fretflex.ui.tuner;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.allen508.fretflex.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScaleSpinner {

    private View view;
    private TunerViewModel viewModel;

    ScaleSpinner(View view, TunerViewModel viewModel){
        this.view = view;
        this.viewModel = viewModel;
    }

    void init() {
        addScalesToSpinner(this.view, this.viewModel);
        addListenerOnSpinnerItemSelection(view);
    }

    // add items into spinner dynamically
    private void addScalesToSpinner(View view, TunerViewModel viewModel) {

        ArrayList<String> scaleNames = new ArrayList<String>();
        for (Scale scale : viewModel.getScales()) {
            scaleNames.add(scale.getName());
        }


        Spinner spinner = view.findViewById(R.id.scale_spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, scaleNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void addListenerOnSpinnerItemSelection(View view) {
        Spinner spinner = view.findViewById(R.id.scale_spinner);
        spinner.setOnItemSelectedListener(new ScaleOnItemSelectedListener());
    }


}
