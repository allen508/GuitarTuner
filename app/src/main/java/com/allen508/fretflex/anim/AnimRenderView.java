package com.allen508.fretflex.anim;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.ref.WeakReference;
import java.util.List;

public abstract class AnimRenderView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "ANIM";

    private boolean isStartAnim = false;
    private final static Object surfaceLock = new Object();
    private RenderThread renderThread;

    protected abstract void doDrawBackground(Canvas canvas);
    protected abstract void onRender(Canvas canvas, long millisPassed);

    public AnimRenderView(Context context) {
        this(context, null);
    }

    public AnimRenderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimRenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        renderer = onCreateRenderer();
        if (renderer != null && renderer.isEmpty()) {
            throw new IllegalStateException();
        }

        renderThread = new RenderThread(this);
    }

    public void onResume(){
        synchronized (surfaceLock){
            if (renderThread != null) {
                renderThread.isPause = false;
                surfaceLock.notifyAll();
            }
        }
    }

    public void onPause(){
        synchronized (surfaceLock){
            if (renderThread != null) {
                renderThread.isPause = true;
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
            renderThread.setRun(false);
            renderThread.destoryed = true;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && isStartAnim){
            startAnim();
        }else {
            startThread();
        }
    }


    public interface IRenderer {
        void onRender(Canvas canvas, long millisPassed);
    }

    private List<IRenderer> renderer;

    protected List<IRenderer> onCreateRenderer() {
        return null;
    }

    private void render(Canvas canvas, long millisPassed) {
        if (renderer != null) {
            for (int i = 0, size = renderer.size(); i < size; i++) {
                renderer.get(i).onRender(canvas, millisPassed);
            }
        } else {
            onRender(canvas, millisPassed);
        }
    }

    public void startAnim(){
        isStartAnim = true;
        startThread();
    }

    private void startThread(){

        if (renderThread != null && !renderThread.running) {
            renderThread.setRun(true);
            try {
                if (renderThread.getState() == Thread.State.NEW) {
                    renderThread.start();
                }

            }catch (RuntimeException e){
                e.printStackTrace();
            }

        }
    }

    public void stopAnim(){
        isStartAnim = false;
        if (renderThread != null && renderThread.running) {
            renderThread.setRun(false);
            renderThread.interrupt();
        }
    }

    public boolean isRunning(){
        if (renderThread != null) {
            return renderThread.running;
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



    private static class RenderThread extends Thread {

        private static final long SLEEP_TIME = 16;

        private WeakReference<AnimRenderView> renderView;
        private boolean running = false;
        private boolean destoryed = false;
        private boolean isPause = false;
        public RenderThread(AnimRenderView renderView) {
            super("RenderThread");
            Log.e(TAG, "RenderThread: " );
            this.renderView = new WeakReference<>(renderView);
        }

        private SurfaceHolder getSurfaceHolder(){
            if (getRenderView() != null ){
                return getRenderView().getHolder();
            }
            return null;
        }

        private AnimRenderView getRenderView(){
            return renderView.get();
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
                        if (getSurfaceHolder() != null && getRenderView() != null) {
                            Canvas canvas = getSurfaceHolder().lockCanvas();
                            if (canvas != null) {
                                getRenderView().doDrawBackground(canvas);
                                if (getRenderView().isStartAnim) {
                                    // do something real draw here
                                    getRenderView().render(canvas, System.currentTimeMillis() - startAt);
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
