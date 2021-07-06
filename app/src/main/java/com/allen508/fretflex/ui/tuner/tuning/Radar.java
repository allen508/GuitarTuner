package com.allen508.fretflex.ui.tuner.tuning;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

public class Radar {

    public void update(){

    }

    private void setGridLines(Canvas canvas, byte[] sample) {

        Paint linePaint = new Paint();
        linePaint.setColor(Color.WHITE);
        linePaint.setAlpha(50);
        linePaint.setStrokeWidth(20f);
        linePaint.setStyle(Paint.Style.STROKE);

        byte[] source = sample;
        int CHUNK_COUNT = 20;
        int CHUNK_SIZE = source.length/CHUNK_COUNT;

        byte[][] ret = new byte[(int)Math.ceil(source.length / (double)CHUNK_SIZE)][];
        int start = 0;
        for(int i = 0; i < ret.length; i++) {
            if(start + CHUNK_SIZE > source.length) {
                ret[i] = new byte[source.length-start];
                System.arraycopy(source, start, ret[i], 0, source.length - start);
            }
            else {
                ret[i] = new byte[CHUNK_SIZE];
                System.arraycopy(source, start, ret[i], 0, CHUNK_SIZE);
            }
            start += CHUNK_SIZE ;
        }

        float num[] = new float[CHUNK_COUNT];

        for(int i = 0; i < ret.length-1; i++) {
            for(int n = 0; n < ret[i].length-1; n++) {
            num[i] =+ ret[i][n];
        }}

        for(int i = 0; i < num.length-1; i++) {

            Path linePath = new Path();
            linePath.moveTo((i*30)+200, 1500);
            linePath.lineTo((i*30)+200, ((num[i]*-1)*3)+1500);
            canvas.drawPath(linePath, linePaint);

        }



    }


    public void draw(Canvas canvas, byte[] sample){

        setGridLines(canvas, sample);
    }


}
