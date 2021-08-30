package com.allen508.fretflex.ui.tuner;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.allen508.fretflex.R;
import com.allen508.fretflex.data.Note;
import com.allen508.fretflex.data.NoteRepository;
import com.allen508.fretflex.sampler.FrequencyAnalyser;
import com.allen508.fretflex.sampler.TuningUtils;

import java.util.List;


public class TunerViz {

    private TuningUtils utils;
    private Context context;
    private Drawable logo;
    private NoteRepository repo;
    private List<Note> tuningNotes;

    public TunerViz(Context context){
        this.context = context;
        this.utils = new TuningUtils();
        //this.logo = context.getResources().getDrawable(R.drawable.logo, null);
        this.repo = new NoteRepository();
    }

    public void draw(Canvas canvas, FrequencyAnalyser tuner, String tuningName) {

        drawBackground(canvas);

        tuningNotes = repo.getTuning(tuningName);

        float detectedFrequency = tuner.getDetectedFrequency();
        TuningUtils.Difference diff = utils.tuneToAlternative(detectedFrequency, tuningName, tuningNotes);

        TunerVisual tunerVisual = new TunerVisual(canvas);
        tunerVisual.update(detectedFrequency, diff.getHz(), diff.getReferenceNote(), tuner.getPitchHoldCounter());
        tunerVisual.draw();

    }

    private void drawBackground(Canvas canvas){
        canvas.drawColor(Color.rgb(0, 0, 0));
    }

    private class TunerVisual{

        private final Coord2d coord;
        private Canvas canvas;
        private float detectedFrequency;
        private float diffHz;
        private Note referenceNote;
        private int pitchHoldCounter;

        public TunerVisual(Canvas canvas){
            this.canvas = canvas;
            this.coord = new Coord2d(canvas);
            this.coord.setCanvasCentrePoint(this.coord.centrePoint.x, this.coord.centrePoint.y -100);
        }

        public void update(float detectedFrequency, float diffHz, Note referenceNote, int pitchHoldCounter){
            this.detectedFrequency = detectedFrequency;
            this.diffHz = diffHz;
            this.referenceNote = referenceNote;
            this.pitchHoldCounter = pitchHoldCounter;
        }

        public void draw(){

            //drawRadar();
            //drawTunerBackground();
            //if(pitchHoldCounter > 0) {
            //    drawNeedle();
            //    drawReferenceNote();
            //}
            //drawTuning();
            //drawHint();

            int i = 0;
            int lineHeight = 150;
            for (Note note: tuningNotes) {

                if(isActiveNote(note, referenceNote) && pitchHoldCounter > 0) {
                    drawActiveString(i, lineHeight);
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

        }

        private void drawActiveTuningHint(int noteIndex, int lineHeight){

            int x = 100+200;
            int blockSize = lineHeight;
            int y = 25+(noteIndex * blockSize) + 200;

            String hintText = "";

            if(diffHz > 0){
                hintText = "Too high";
            }

            if(diffHz < 0){
                hintText = "Too low";
            }

            int textColour = Color.rgb(177, 75, 41);

            VisualText text = new VisualText(x + 200, y, hintText,50, textColour);
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
            paint.setStrokeWidth(12);

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

                text = new VisualText(x, y, note.getName(),100, textColour);
                text.x = x - text.width/2;
                text.y = y - text.height/2;
                text.draw();

            }

            public void draw(){
                drawDropShadow();
                drawCircle();
                drawText();
            }


        }


        public class VisualString{

            public VisualString(boolean isActive){

            }

            public void draw(){

            }

        }


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

                staticLayout = new StaticLayout(value, textPaint, (int) width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);
                // width = staticLayout.getWidth();
                width = (int) textPaint.measureText(value);
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


    private class Coord2d {
        private Canvas canvas;

        private int width;
        private int height;

        private Vector2d centrePoint;

        public Coord2d(Canvas canvas){

            this.canvas = canvas;
            this.width = canvas.getWidth();
            this.height = canvas.getHeight();
            this.centrePoint = new Vector2d(this.width/2, this.height/2);
        }

        public Vector2d getCanvasCentrePoint(){
            return this.centrePoint;
        }

        public void setCanvasCentrePoint(int x, int y){
            this.centrePoint = new Vector2d(x, y);
        }


        public CirclePoints getCirclePoints(int radius, Vector2d centrePoint){
            return new CirclePoints(centrePoint.x, centrePoint.y, radius);
        }

        public ArcPoints getArchPoints(int radius, Vector2d centrePoint, float startAngle, float sweepAngle ){

            float left = centrePoint.x - radius;
            float top = centrePoint.y - radius;
            float right = centrePoint.x + radius;
            float bottom = centrePoint.y + radius;

            startAngle = startAngle - 90;

            return new ArcPoints(left, top, right, bottom, startAngle, sweepAngle, true);
        }
    }


    private class ArcPoints{

        private float left;
        private float top;
        private float right;
        private float bottom;
        private float startAngle;
        private float sweepAngle;
        private boolean useCentre;

        public ArcPoints(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCentre){

            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.startAngle = startAngle;
            this.sweepAngle = sweepAngle;
            this.useCentre = useCentre;
        }


        public float getBottom() {
            return bottom;
        }

        public float getLeft() {
            return left;
        }

        public float getRight() {
            return right;
        }

        public float getTop() {
            return top;
        }

        public float getStartAngle() {
            return startAngle;
        }

        public float getSweepAngle() {
            return sweepAngle;
        }

        public boolean getUseCentre() {
            return useCentre;
        }


    }

    private class CirclePoints{

        private int cx;
        private int cy;
        private int radius;

        public CirclePoints(int cx, int cy, int radius){

            this.cx = cx;
            this.cy = cy;
            this.radius = radius;
        }

        public int getCx() {
            return cx;
        }

        public int getCy() {
            return cy;
        }

        public int getRadius() {
            return radius;
        }
    }


    private class Vector2d{
        private int x;
        private int y;

        public Vector2d(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

}
