package com.allen508.fretflex.ui.tuner;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.allen508.fretflex.data.Note;
import com.allen508.fretflex.sampler.FrequencyAnalyser;
import com.allen508.fretflex.sampler.TuningUtils;

public class TunerViz {


    public void draw(Canvas canvas, FrequencyAnalyser tuner) {

        float frequency = tuner.getDetectedFrequency();

        if(tuner.getPitchHoldCounter() >= 0 ) {

            TuningUtils utils = new TuningUtils();
            TuningUtils.Difference diff = utils.tuneToStandard(frequency);

            float angle = (float)diff.getIntervalRatio() * 60;
            drawArc(canvas, angle, 200, 800);

            String tuneDirectionText = "";

            if(utils.isTuned(diff)){
                tuneDirectionText = "In Tune";
            } else {
                if(diff.getHz() > 0){
                    tuneDirectionText = "Tune Down";
                }
                if(diff.getHz() < 0){
                    tuneDirectionText = "Tune Up";
                }
            }

            drawText(canvas, noteToString(diff.getPreviousNoteFromReference()), 100, 20);
            drawText(canvas, noteToString(diff.getReferenceNote()), 100, 120);
            drawText(canvas, tuneDirectionText, 500, 120);
            drawText(canvas, noteToString(diff.getNextNoteFromReference()), 100, 220);

            drawText(canvas, String.valueOf((int)diff.getHz()), 100, 320);
        }

        drawText(canvas, String.valueOf((int)frequency), 100, 600);

    }

    private String noteToString(Note note){
        return note.getName() + " " + note.getAccidental() + " " + (int)note.getFrequency();
    }


    private void drawArc(Canvas canvas, float angle, int x, int y){
        final int strokeWidth = 40;

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAlpha(50);
        paint.setStrokeWidth(20f);
        paint.setStyle(Paint.Style.STROKE);

        //size 200x200 example
        RectF rect = new RectF(strokeWidth, strokeWidth, 500 + strokeWidth, 500 + strokeWidth);

        int START_ANGLE_POINT = 270;

        canvas.save();
        canvas.translate(x, y);
        canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint);
        canvas.restore();
    }


    private void drawText(Canvas canvas, String value, int x, int y)
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
