package com.allen508.fretflex.ui.tuner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import javax.annotation.Nullable;
import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TunerViewModel extends ViewModel {


    private @Nullable ArrayList<Scale> scales;
    private @Nullable String selectedScale;

    @Inject
    public TunerViewModel(SavedStateHandle handle){

        initScalesData();
    }

    private void initScalesData(){

        Scale scale1 = new Scale("Standard", "E", "A", "D", "G", "B", "E");
        Scale scale2 = new Scale("Drop D", "D", "A", "D", "G", "B", "E");

        this.scales =  new ArrayList<Scale>();
        this.scales.add(scale1);
        this.scales.add(scale2);
    }

    public LiveData<SampleResult> getFrequency() {

        SampleResult sampleResult = new SampleResult();
        sampleResult.Frequency = 432.4;

        return new MutableLiveData<SampleResult>(sampleResult);
    }

    public ArrayList<Scale> getScales() {
        return scales;
    }

    public void setSelectedScale(String name) {
        this.selectedScale = name;
    }

    public Scale getSelectedScale() {

        Scale selectedScale = null;

        for(Scale scale: this.scales){
            if(scale.getName() == this.selectedScale) {
                selectedScale = scale;
            }
        }

        return selectedScale;

    }


}



