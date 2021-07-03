package com.allen508.fretflex.ui.tuner.tuning;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

public class Background {


    void draw(Canvas canvas) {

        //LinearGradient gradient = new LinearGradient(0, 0, canvas.getWidth(), canvas.getHeight(),0x30323C, 0xABAAA1, Shader.TileMode.REPEAT);

        Paint p = new Paint();

        p.setShader(new LinearGradient(0,0,canvas.getWidth()/2, canvas.getHeight()/2,
                new int[]{0x30323C, 0xFFFFFF},null, Shader.TileMode.CLAMP));
        //p.setDither(true);
        //p.setShader(gradient);

        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), p);

    }

}


