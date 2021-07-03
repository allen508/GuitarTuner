package com.allen508.fretflex.game;

import android.content.Context;
import android.graphics.Canvas;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

public abstract class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private static final String TAG = "GAMEVIEW";

    private boolean isRunning = false;
    private final static Object surfaceLock = new Object();

    protected GameThread gameThread;
    protected abstract void onDraw(Canvas canvas, long millisPassed);
    protected abstract void onUpdate(long millisPassed);

    public GameSurfaceView(Context context) {
        this(context, null);
    }

    public GameSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(this);
    }

    public void onResume(){
        synchronized (surfaceLock){
            if (gameThread != null) {
                gameThread.isPause = false;
                surfaceLock.notifyAll();
            }
        }
    }

    public void onPause(){
        synchronized (surfaceLock){
            if (gameThread != null) {
                gameThread.isPause = true;
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Get here the wide High information SurfaceView
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        synchronized (surfaceLock) {// here need to lock, otherwise there might crash doDraw
            gameThread.setRun(false);
            gameThread.destoryed = true;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && this.isRunning){
            startGameView();
        }else {
            startThread();
        }
    }

    private void update(long millisPassed) {
        this.onUpdate(millisPassed);
    }

    private void draw(Canvas canvas, long millisPassed) {
        this.onDraw(canvas, millisPassed);
    }

    public void startGameView(){
        isRunning = true;
        startThread();
    }

    private void startThread(){

        if (gameThread != null && !gameThread.running) {
            gameThread.setRun(true);
            try {
                if (gameThread.getState() == Thread.State.NEW) {
                    gameThread.start();
                }

            }catch (RuntimeException e){
                e.printStackTrace();
            }

        }

    }

    public void stopGameView(){
        this.isRunning = false;
        if (gameThread != null && gameThread.running) {
            gameThread.setRun(false);
            gameThread.interrupt();
        }
    }

    public boolean isRunning(){
        if (gameThread != null) {
            return gameThread.running;
        }
        return false;
    }

    // release resources, prevent memory leaks
    public void release(){
        if (getHolder() != null && getHolder().getSurface() != null) {
            getHolder().getSurface().release();
            getHolder().removeCallback(this);
        }
    }


    private class GameThread extends HandlerThread {

        private static final long SLEEP_TIME = 16;

        private WeakReference<GameSurfaceView> gameView;
        private boolean running = false;
        private boolean destoryed = false;
        private boolean isPause = false;

        public GameThread(GameSurfaceView gameView) {
            super("GameThread");
            Log.e(TAG, "GameThread: " );
            this.gameView = new WeakReference<>(gameView);
        }

        private SurfaceHolder getSurfaceHolder(){
            if (getGameView() != null ){
                return getGameView().getHolder();
            }
            return null;
        }

        private GameSurfaceView getGameView(){
            return gameView.get();
        }

        @Override
        public void run() {

            long startAt = System.currentTimeMillis();
            while (!destoryed) {
                // Log.e ( "whileThread", "RenderView animation thread 222");
                synchronized (surfaceLock) {
                    // here and no real end Thread, to prevent part of the phone calls the same continuous error Thread
                    while (isPause){
                        try {
                            surfaceLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (running) {
                        if (getSurfaceHolder() != null && getGameView() != null) {
                            Canvas canvas = getSurfaceHolder().lockCanvas();
                            if (canvas != null) {

                                if (getGameView().isRunning()) {
                                    getGameView().update(System.currentTimeMillis() - startAt);
                                    getGameView().draw(canvas, System.currentTimeMillis() - startAt);
                                }
                                if(getSurfaceHolder().getSurface().isValid()){
                                    getSurfaceHolder().unlockCanvasAndPost(canvas);
                                }
                            }
                        }else {
                            running = false;
                        }

                    }

                }
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }


        public void setRun(boolean isRun) {
            this.running = isRun;
        }

    }


}
