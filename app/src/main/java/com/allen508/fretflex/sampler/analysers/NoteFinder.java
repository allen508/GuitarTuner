package com.allen508.fretflex.sampler.analysers;

import com.allen508.fretflex.sampler.Scale;

public class NoteFinder {

    private Scale scale;

    public NoteFinder(Scale scale) {
        this.scale = scale;
    }

    public String getNearestNote(){
        return scale.getNotes().get(0);
    }

}
