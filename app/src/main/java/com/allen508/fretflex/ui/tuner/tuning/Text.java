package com.allen508.fretflex.ui.tuner.tuning;

import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

public class Text {

    public void draw(Canvas canvas, TextPaint paint, String value, int left, int top) {

        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(100);
        textPaint.setColor(0xFFFFFFFF);

        int width = (int) textPaint.measureText(value);
        StaticLayout staticLayout = new StaticLayout(value, textPaint, (int) width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);

        canvas.save();
        canvas.translate(left, top);
        staticLayout.draw(canvas);
        canvas.restore();
    }
}
