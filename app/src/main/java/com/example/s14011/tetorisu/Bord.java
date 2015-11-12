package com.example.s14011.tetorisu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by s14011 on 15/11/11.
 */
public class Bord extends SurfaceView implements SurfaceHolder.Callback{
    public static final int FPS = 30;
    private SurfaceHolder holder;
    private DrawThread thread;
    private Bitmap blocks;
    private Rect blockRect;
    private Rect[] blockRectArray = new Rect[8];



    public Bord(Context context) {
        super(context);
        initialize(context);
    }

    public Bord(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public Bord(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
        startThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public void surfaceDestroyed(final SurfaceHolder holder) {
            stopThread();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas == null) {
            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        canvas.drawColor(Color.BLACK);
        float side = width / 10.0f;
        RectF destRect = new RectF(0, 0, side, side);
        Paint paint = new Paint();

        canvas.drawBitmap(blocks, blockRectArray[0], destRect, paint);

        destRect.offset(side, side);
        canvas.drawBitmap(blocks, blockRectArray[1], destRect, paint);

        destRect.offset(side, side);
        canvas.drawBitmap(blocks, blockRectArray[2], destRect, paint);

        destRect.offset(side, side);
        canvas.drawBitmap(blocks, blockRectArray[3], destRect, paint);

        destRect.offset(side, side);
        canvas.drawBitmap(blocks, blockRectArray[4], destRect, paint);

        destRect.offset(side, side);
        canvas.drawBitmap(blocks, blockRectArray[5], destRect, paint);

        destRect.offset(side, side);
        canvas.drawBitmap(blocks, blockRectArray[6], destRect, paint);

        destRect.offset(side, side);
        canvas.drawBitmap(blocks, blockRectArray[7], destRect, paint);

    }

    private void initialize(Context context) {
        getHolder().addCallback(this);
        blocks = BitmapFactory.decodeResource(context.getResources(),R.drawable.block);
        int side = blocks.getWidth();
        for (int i = 0; i < blockRectArray.length; i++) {
            blockRectArray[i] = new Rect(0, i * side, side, (i + 1) * side);
        }
    }

    private void startThread() {
        stopThread();

        thread = new DrawThread();
        thread.start();
    }

    private void stopThread() {
        if (thread != null) {
            thread.isFinished = true;
            thread = null;
        }
    }
    private class DrawThread extends Thread {
        private boolean isFinished;

        @Override
        public void run() {
            long prevTime = 0;
            while (!isFinished) {
                if (holder == null ||
                        System.currentTimeMillis() - prevTime <  1000 / FPS) {
                    try {
                        sleep(1000 / FPS / 3);
                    }catch (InterruptedException e) {
                        Log.w("DrawThread", e.getMessage(), e);
                    }
                    continue;
                }
                Canvas c = null;
                try {
                    c = holder.lockCanvas(null);
                    synchronized (holder){
                        draw(c);
                    }
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
                prevTime = System.currentTimeMillis();
            }
        }
    }
}

