package com.allen508.fretflex.ui.tuner.tuning;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.allen508.fretflex.game.GameSurfaceView;
import com.allen508.fretflex.ui.tuner.sampling.OnSampleListener;

public class TunerView extends GameSurfaceView implements OnSampleListener {

    private Text frequencyText;
    private Text nearestNoteText;
    //private Radar radar;
    private TunerViz tunerViz;

    private byte[] sample;
    private double frequency;
    private int noteIndex;
    private String nearestNote;

    public TunerView(Context context) {
        super(context);
    }
    public TunerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);

        this.frequencyText = new Text();
        this.tunerViz = new TunerViz();
        //this.radar = new Radar();
    }

    @Override
    public void startGameView() {
        super.startGameView();
    }

    @Override
    protected void onUpdate(long millisPassed) {
    }

    @Override
    protected void onDraw(Canvas canvas, long millisPassed) {

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);

        //radar.draw(canvas, this.sample);
        this.tunerViz.draw(canvas, frequency, this.noteIndex);
        //frequencyText.draw(canvas, null, String.valueOf(frequency), 100, 100);
        //nearestNoteText.draw(canvas, null, String.valueOf(nearestNote), 100, 300);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public boolean onSample(byte[] sample, double frequency, int noteIndex) {
        this.sample = sample;
        this.frequency = frequency;
        this.noteIndex = noteIndex;
        return true;
    }
}
