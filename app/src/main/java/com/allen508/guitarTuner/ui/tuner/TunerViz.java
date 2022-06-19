package com.allen508.guitarTuner.ui.tuner;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;


import com.allen508.guitarTuner.data.Note;
import com.allen508.guitarTuner.data.NoteRepository;
import com.allen508.guitarTuner.sampler.FrequencyAnalyser;
import com.allen508.guitarTuner.sampler.TuningUtils;

import java.util.List;


public class TunerViz {

    private TuningUtils utils;
    private Context context;
    private NoteRepository repo;
    private List<Note> tuningNotes;

    public TunerViz(Context context){
        this.context = context;
        this.utils = new TuningUtils();
        this.repo = new NoteRepository();
    }

    public void draw(Canvas canvas, FrequencyAnalyser tuner, String tuningName) {

        drawBackground(canvas);

        tuningNotes = repo.getTuning(tuningName);

        float detectedFrequency = tuner.getDetectedFrequency();
        int holdCount = tuner.getPitchHoldCounter();

        //detectedFrequency = 82.2f;
        //holdCount = 2;

        TuningUtils.Difference diff = utils.tuneToAlternative(detectedFrequency, tuningName, tuningNotes);

        TunerVisual tunerVisual = new TunerVisual(canvas);
        tunerVisual.update(detectedFrequency, diff, holdCount);
        tunerVisual.draw();

    }

    private void drawBackground(Canvas canvas){
        canvas.drawColor(Color.rgb(0, 0, 0));
    }

    private class TunerVisual{

        //private final Coord2d coord;
        private Canvas canvas;
        private float detectedFrequency;
        private float diffHz;
        private Note referenceNote;
        private TuningUtils.Difference diff;
        private int pitchHoldCounter;

        public TunerVisual(Canvas canvas){
            this.canvas = canvas;
            //this.coord = new Coord2d(canvas);
            //this.coord.setCanvasCentrePoint(this.coord.centrePoint.x, this.coord.centrePoint.y -100);
        }

        public void update(float detectedFrequency, TuningUtils.Difference diff, int pitchHoldCounter){
            this.detectedFrequency = detectedFrequency;
            this.diffHz = diff.getHz();
            this.referenceNote = diff.getReferenceNote();
            this.diff = diff;
            this.pitchHoldCounter = pitchHoldCounter;
        }

        public void draw(){

            int i = 0;
            int lineHeight = 150;
            for (Note note: tuningNotes) {

                if(isActiveNote(note, referenceNote) && pitchHoldCounter > 0) {
                    if(utils.isTuned(diff)){
                        drawInTuneString(i, lineHeight);
                    } else {
                        drawActiveString(i, lineHeight);
                    }
                } else {
                    drawInactiveString(i, lineHeight);
                }

                drawNote(i, lineHeight, note);

                i++;
            }

        }


        private boolean isActiveNote(Note note, Note referenceNote){
            return note.getName() == referenceNote.getName() && note.getAccidental() == referenceNote.getAccidental() && note.getOctave() == referenceNote.getOctave();
        }


        private void drawInTuneString(int noteIndex, int lineHeight){

            int x = 300;
            int blockSize = lineHeight;
            int y = (noteIndex * blockSize) +200;

            int color = Color.rgb(20, 249, 85);

            Paint paint = new Paint();
            paint.setColor(color);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(12);

            Path path = new Path();
            path.moveTo(x - 100, y);
            path.lineTo(x + 600, y);

            canvas.drawPath(path, paint);

            int textColour = Color.rgb(255, 255, 255);
            String message = "In Tune";

            VisualText text = new VisualText(x + 400, y + 15, message,50, textColour);
            text.draw();

        }



        private void drawActiveString(int noteIndex, int lineHeight){

            int curveHeight = 250; //Zeroed at 250
            int scaleFactor = 30;
            int max = 300;
            int scaledDiff = (int)((this.diffHz*-1) * scaleFactor);

            int x = 300;
            int blockSize = lineHeight;
            int y = (noteIndex * blockSize)+200;

            if(scaledDiff > max) {
                scaledDiff = max;
            }

            if(scaledDiff < (max * -1)) {
                scaledDiff = (max * -1);
            }

            curveHeight = y + scaledDiff;

            int color = Color.rgb(137, 95, 53);

            Paint paint = new Paint();
            paint.setColor(color);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(12);

            Path path = new Path();
            path.moveTo(x - 100, y);
            path.cubicTo(x + 600, y, x+ 600, curveHeight, x+ 600, y);

            canvas.drawPath(path, paint);


            drawTuningHint(noteIndex, lineHeight);

        }

        private void drawTuningHint(int noteIndex, int lineHeight){

            int x = 300;
            int blockSize = lineHeight;
            int y = (noteIndex * blockSize) + 200;

            String hintText = "";

            if(diffHz > 0){
                hintText = "Tune down";
                x = x - 90;
            }

            if(diffHz < 0){
                hintText = "Tune up";
            }

            int textColour = Color.rgb(255, 255, 255);

            VisualText text = new VisualText(x + 400, y + 15, hintText,50, textColour);
            text.draw();

        }


        private void drawNote(int noteIndex, int lineHeight, Note note){

            int x = 200;
            int blockSize = lineHeight;
            int y = (noteIndex * blockSize) + 200;

            VisualNote visualNote = null;

            if(isActiveNote(note, referenceNote)) {
                visualNote = new VisualNote(note, x, y, true, diffHz);
            } else {
                visualNote = new VisualNote(note, x, y, false, 0);
            }

            visualNote.draw();

        }


        private void drawInactiveString(int noteIndex, int lineHeight){

            int x = 300;
            int blockSize = lineHeight;
            int y = (noteIndex * blockSize) +200;

            int color = Color.rgb(250, 250, 250);

            Paint paint = new Paint();
            paint.setColor(color);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(12 - (noteIndex*2));

            Path path = new Path();
            path.moveTo(x - 100, y);
            path.lineTo(x + 600, y);

            canvas.drawPath(path, paint);
        }

        private class VisualNote{

            public int x;
            public int y;
            public int width = 120;

            private boolean active;
            private float diffHz;

            public Note note;

            private int circleColor = Color.rgb(204, 204, 204);
            private int circleShadowColor = Color.rgb(20, 20, 20);
            private int textColour = Color.rgb(0, 0, 0);

            public Paint circlePaint = new Paint();

            public VisualText text;

            public VisualNote(Note note, int x, int y, boolean active, float diffHz) {
                this.note = note;
                this.x = x;
                this.y = y;
                this.active = active;
                this.diffHz = diffHz;
            }

            private void drawCircle(){

                circlePaint = new Paint();
                circlePaint.setColor(circleColor);
                circlePaint.setAntiAlias(true);

                canvas.drawCircle(x, y, width/2, circlePaint);
            }

            private void drawDropShadow(){

                circlePaint = new Paint();
                circlePaint.setColor(circleShadowColor);
                circlePaint.setAntiAlias(true);
                circlePaint.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL));

                canvas.drawCircle(x +10, y+10, width/2, circlePaint);

            }

            private void drawText(){

                text = new VisualText(x, y, note.getName(),80, textColour);
                text.x = x - text.width/2;
                text.y = y - text.height/2;
                text.draw();

                VisualText text1 = new VisualText(x + 10, y - 50, note.getAccidental(),50, textColour);
                text1.draw();

            }

            public void draw(){
                drawDropShadow();
                drawCircle();
                drawText();
            }


        }


        //KEEP
        private class VisualText{

            private int x;
            private int y;
            private String value;
            private int textSize;
            private int color;
            private StaticLayout staticLayout;

            public int width;
            public int height;

            public VisualText(int x, int y, String value, int textSize, int color){
                this.x = x;
                this.y = y;
                this.value = value;
                this.textSize = textSize;
                this.color = color;

                init();
            }

            private StaticLayout init() {

                TextPaint textPaint = new TextPaint();
                textPaint.setAntiAlias(true);
                textPaint.setTextSize(textSize);
                textPaint.setColor(color);

                width = (int) textPaint.measureText(value);
                staticLayout = new StaticLayout(value, textPaint, (int) width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);

                width = staticLayout.getWidth();
                height = staticLayout.getHeight();

                return staticLayout;
            }

            public void draw() {
                canvas.save();
                canvas.translate(x, y);
                staticLayout.draw(canvas);
                canvas.restore();
            }

        }


    }


}
