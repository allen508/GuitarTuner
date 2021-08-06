package com.allen508.fretflex.ui.tuner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
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
        //drawLogo(canvas);
        tuningNotes = repo.getTuning(tuningName);

        float detectedFrequency = tuner.getDetectedFrequency();
        TuningUtils.Difference diff = utils.tuneToAlternative(detectedFrequency, tuningName, tuningNotes);

        TunerVisual tunerVisual = new TunerVisual(canvas);
        tunerVisual.update(detectedFrequency, diff.getHz(), diff.getReferenceNote());
        tunerVisual.draw();

    }

    private void drawLogo(Canvas canvas){
        logo.setBounds(0, 50, canvas.getWidth(), (int)(canvas.getWidth()/2.5));
        logo.draw(canvas);
    }

    private void drawBackground(Canvas canvas){
        canvas.drawColor(Color.rgb(249, 249, 249));
    }

    private class TunerVisual{

        private final Coord2d coord;
        private Canvas canvas;
        private float detectedFrequency;
        private float diffHz;
        private Note referenceNote;

        public TunerVisual(Canvas canvas){
            this.canvas = canvas;
            this.coord = new Coord2d(canvas);
            this.coord.setCanvasCentrePoint(this.coord.centrePoint.x, this.coord.centrePoint.y - 200);
        }

        public void update(float detectedFrequency, float diffHz, Note referenceNote){
            this.detectedFrequency = detectedFrequency;
            this.diffHz = diffHz;
            this.referenceNote = referenceNote;
        }

        public void draw(){
            drawRadar();
            drawTunerBackground();
            drawNeedle();
            drawReferenceNote();
            drawTuning();
            drawHint();
        }

        private void drawTunerBackground(){

            int color = Color.rgb(177, 75, 41);

            Paint paint = new Paint();
            paint.setColor(color);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);

            paint.setStrokeWidth(5);
            CirclePoints circlePoints = coord.getCirclePoints(400, coord.getCanvasCentrePoint());
            canvas.drawCircle(circlePoints.cx, circlePoints.cy, circlePoints.radius, paint);

            paint.setStrokeWidth(8);
            circlePoints = coord.getCirclePoints(300, coord.getCanvasCentrePoint());
            canvas.drawCircle(circlePoints.cx, circlePoints.cy, circlePoints.radius, paint);
        }

        private void drawDetectedFrequency(){
            int x = 240;
            int y = 1500;
            StaticLayout sl = drawTextStart(String.valueOf((int)detectedFrequency), x, y, 260, Color.rgb(177, 75, 41));
            x = (canvas.getWidth() / 2) - (sl.getWidth()/2);
            drawTextCommit(x, y, sl);

            StaticLayout sl1 = drawTextStart("hz", x + 500, y + 160, 100, Color.rgb(177, 75, 41));
            drawTextCommit(x + 500, y + 160, sl1);

        }

        private void drawNeedle(){

            canvas.save();

            int color = Color.rgb(37, 37, 39);

            Paint paint = new Paint();
            paint.setColor(color);
            paint.setStrokeWidth(coord.width/200);
            paint.setAntiAlias(true);

            float markSize = 100;

            float radius = 400;

            int i = (int)this.diffHz;
            float angle = (float) Math.toRadians(i);

            float startX = (float) (coord.getCanvasCentrePoint().x + radius * Math.sin(angle));
            float startY = (float) (coord.getCanvasCentrePoint().y - radius * Math.cos(angle));

            float stopX = (float) (coord.getCanvasCentrePoint().x + (radius - markSize) * Math.sin(angle));
            float stopY = (float) (coord.getCanvasCentrePoint().y - (radius - markSize) * Math.cos(angle));

            canvas.drawLine(startX, startY, stopX, stopY, paint);
            canvas.restore();
        }


        private void drawRadar(){

            int radius = 400;

            Paint paint = new Paint();
            paint.setColor(Color.rgb(177, 75, 41));
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setShader(new LinearGradient(0, coord.centrePoint.y, 0, coord.centrePoint.y + radius, Color.rgb(160, 198, 107), Color.argb(0, 160, 198, 107), Shader.TileMode.REPEAT));

            ArcPoints arcPoints = coord.getArchPoints(radius, coord.getCanvasCentrePoint(), -10, 20);
            canvas.drawArc(arcPoints.left, arcPoints.top, arcPoints.right, arcPoints.bottom, arcPoints.startAngle, arcPoints.sweepAngle, arcPoints.useCentre, paint);

        }

        private void drawTuning(){
            int x = coord.getCanvasCentrePoint().x;
            int y = coord.getCanvasCentrePoint().y + 500;

            int lineMarginY = 130;

            int textColor = Color.rgb(177, 75, 41);
            int textSize = 100;
            int aTextSize = 50;
            int blockSize = 90;


            int lineColor = Color.rgb(248, 94, 42);
            Paint linePaint = new Paint();
            linePaint.setColor(lineColor);
            linePaint.setStrokeWidth(15);
            linePaint.setAntiAlias(true);
            linePaint.setStrokeCap(Paint.Cap.ROUND);
            linePaint.setStyle(Paint.Style.FILL);

            x = x - (3*blockSize);

            for (Note note : tuningNotes) {
                StaticLayout sl1 = drawTextStart(note.getName(), x, y, textSize, textColor);
                drawTextCommit(x, y, sl1);

                int aX = x + 50;
                int aY = y - 5;

                StaticLayout sl2 = drawTextStart(note.getAccidental(), aX, aY, aTextSize, textColor);
                drawTextCommit(aX, aY, sl2);

                if(referenceNote.getName() == note.getName() && referenceNote.getOctave() == note.getOctave()){
                    canvas.drawLine(x , y+lineMarginY, x+blockSize-30, y+lineMarginY, linePaint);
                }

                x = x + blockSize;

            }
        }

        private void drawHint(){

            int x = coord.getCanvasCentrePoint().x;
            int y = coord.getCanvasCentrePoint().y - 200;
            int color = Color.rgb(37, 37, 39);

            String hintMessage = "";
            if(diffHz > 0){
                hintMessage = "Tune down";
            }

            if(diffHz < 0){
                hintMessage = "Tune up";
            }

            if(diffHz == 0){
                hintMessage = "In tune - Good job";
            }

            StaticLayout sl = drawTextStart(hintMessage, x, y, 50, color);
            x = x - sl.getWidth()/2;
            drawTextCommit(x, y, sl);
        }


        private void drawReferenceNote(){
            int x = coord.getCanvasCentrePoint().x;
            int y = coord.getCanvasCentrePoint().y - 50;
            int color = Color.rgb(37, 37, 39);


            x = x - 150;

            StaticLayout sl = drawTextStart(referenceNote.getName(), x, y, 180, color);
            drawTextCommit(x, y, sl);

            x = x + 120;
            sl = drawTextStart(String.valueOf(referenceNote.getOctave()), x, y, 50, color);
            drawTextCommit(x, y, sl);

            y = y + 120;
            sl = drawTextStart(referenceNote.getFrequency() + " hz", x, y, 50, color);
            drawTextCommit(x, y, sl);

        }



        private StaticLayout drawTextStart(String value, int x, int y, int textSize, int color)
        {

            TextPaint textPaint = new TextPaint();
            textPaint.setAntiAlias(true);
            textPaint.setTextSize(textSize);
            textPaint.setColor(color);

            int width = (int) textPaint.measureText(value);
            StaticLayout staticLayout = new StaticLayout(value, textPaint, (int) width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);

            return staticLayout;
        }


        private void drawTextCommit(int x, int y, StaticLayout staticLayout)
        {

            canvas.save();
            canvas.translate(x, y);
            staticLayout.draw(canvas);
            canvas.restore();
        }

    }


    private class Coord2d
    {
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
