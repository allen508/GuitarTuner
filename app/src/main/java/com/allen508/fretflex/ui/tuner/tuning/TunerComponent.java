package com.allen508.fretflex.ui.tuner.tuning;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;

import com.allen508.fretflex.game.GameView;
import com.allen508.fretflex.ui.tuner.sampling.SamplerResultReceiver;

public class TunerComponent implements GameView.IGameComponent {

    private SamplerResultReceiver sampleReceiver;
    private Text frequencyText;

    public TunerComponent(SamplerResultReceiver sampleReceiver){
        this.sampleReceiver = sampleReceiver;
    }

    @Override
    public void onUpdate(long millisPassed) {
        //Log.d("CALL", "TunerComponent_onUpdate");
    }

    @Override
    public void onDraw(Canvas canvas, long millisPassed) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
        this.frequencyText = new Text();
        frequencyText.draw(canvas, null, String.valueOf(this.sampleReceiver.getSampleFrequency()), 100, 100);
    }

}
