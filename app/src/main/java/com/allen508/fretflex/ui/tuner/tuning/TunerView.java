package com.allen508.fretflex.ui.tuner.tuning;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;

import com.allen508.fretflex.game.GameView;
import com.allen508.fretflex.ui.tuner.sampling.SamplerResultReceiver;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class TunerView extends GameView {

    private SamplerResultReceiver sampleReceiver;

    public TunerView(Context context) {
        super(context);
    }

    public void setSampleReceiver(SamplerResultReceiver sampleReceiver) {
        this.sampleReceiver = sampleReceiver;
    }

    @Override
    protected void onDraw(Canvas canvas, long millisPassed) {
    }

    @Override
    protected void onUpdate(long millisPassed) {

    }

    @Override
    protected List<IGameComponent> onCreateGameComponents() {

        List<IGameComponent> list = new ArrayList<>();
        list.add(new TunerComponent(this.sampleReceiver));
        return list;
    }

}
