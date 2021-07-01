package com.allen508.fretflex.ui.tuner.mappers;

import com.allen508.fretflex.sampler.Scale;

import java.util.ArrayList;

public class ToSampler {

    public Scale FromViewModelScale(com.allen508.fretflex.ui.tuner.Scale in) {

        if(in == null) {return null;}

        Scale result = new Scale();
        result.setName(in.getName());
        result.setNotes(in.getNotes());

        return result;

    }



}
