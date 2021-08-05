package com.allen508.fretflex.ui.tuner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.allen508.fretflex.R;
import com.allen508.fretflex.sampler.FrequencyAnalyser;

public class TunerSurface extends SurfaceView implements FrequencyAnalyser.FrequencyAnalyserCallback, SurfaceHolder.Callback {

    private TunerViz tunerViz;


    public TunerSurface(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        this.getHolder().addCallback(this);
    }


    protected void draw(FrequencyAnalyser frequencyAnalyser) {

        Canvas canvas = null;
        try {
            canvas = this.getHolder().lockCanvas();

            synchronized (this.getHolder()) {
                if(canvas != null) {
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
                    tunerViz.draw(canvas, frequencyAnalyser);
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
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        tunerViz = new TunerViz(getContext());

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}

