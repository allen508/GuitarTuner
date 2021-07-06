package com.allen508.fretflex.ui.tuner.tuning;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

public class TunerViz {

    private static final double[] FREQUENCIES = { 329.63, 246.94, 196.00, 146.83, 110.00, 82.41};
    private static final String[] NAME        = { "E2",    "B",    "G",   "D",    "A",    "E"};


    public void draw(Canvas canvas, double frequency, int noteIndex) {

        String nearestNote = NAME[noteIndex];
        double noteFrequency = FREQUENCIES[noteIndex];

        final int strokeWidth = 40;

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAlpha(50);
        paint.setStrokeWidth(20f);
        paint.setStyle(Paint.Style.STROKE);


        //size 200x200 example
        RectF rect = new RectF(strokeWidth, strokeWidth, 500 + strokeWidth, 500 + strokeWidth);

        //Initial Angle (optional, it can be zero)
        int angle = 0;
        if(frequency > 0) {
            angle = ((int) frequency) - ((int) noteFrequency);
        }

        int START_ANGLE_POINT = 270;

        canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint);
        showText(canvas, nearestNote + " " + String.valueOf(noteFrequency), 200, 200);
        showText(canvas, "Frequency " + String.valueOf(frequency), 200, 400);
    }

    private void showText(Canvas canvas, String value, int x, int y)
    {

        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(100);
        textPaint.setColor(0xFFFFFFFF);

        int width = (int) textPaint.measureText(value);
        StaticLayout staticLayout = new StaticLayout(value, textPaint, (int) width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);

        canvas.save();
        canvas.translate(x, y);
        staticLayout.draw(canvas);
        canvas.restore();

    }


}
