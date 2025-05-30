package com.allen508.guitarTuner.ui.tuner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import androidx.annotation.NonNull;

import com.allen508.guitarTuner.sampler.FrequencyAnalyser;

public class TunerSurface extends SurfaceView implements FrequencyAnalyser.FrequencyAnalyserCallback, SurfaceHolder.Callback {

    private TunerViz tunerViz;
    private String tuning = "Standard";

    public TunerSurface(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        SurfaceHolder holder = this.getHolder();

        holder.addCallback(this);
        holder.setFixedSize(1000, 1100);

    }

    public void setTuning(String tuning){
        this.tuning = tuning;
    }

    protected void draw(FrequencyAnalyser frequencyAnalyser) {

        Canvas canvas = null;
        try {
            canvas = this.getHolder().lockCanvas();

            synchronized (this.getHolder()) {
                if(canvas != null) {

                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
                    tunerViz.draw(canvas, frequencyAnalyser, tuning);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                this.getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }


    @Override
    public boolean process(FrequencyAnalyser analyser) {

        if(!this.getHolder().getSurface().isValid()) {
            return false;
        }

        draw(analyser);

        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

        }
        return true;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        tunerViz = new TunerViz(getContext());

        holder.setFormat(PixelFormat.TRANSLUCENT);

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}

