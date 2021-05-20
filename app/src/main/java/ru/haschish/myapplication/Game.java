package ru.haschish.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Game extends SurfaceView implements Runnable {
    Thread thread;
    SurfaceHolder holder;
    private volatile boolean isPlay = false;
    float startX = 100;
    float startY = 100;


    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
//        holder.setFormat(PixelFormat.TRANSLUCENT);
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();

            canvas.drawColor(Color.argb(0, 255, 255, 255), PorterDuff.Mode.CLEAR);

            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            Paint paint1 = new Paint();
            paint1.setAlpha(0);
            canvas.drawRect(0, 0, 3000, 3000, paint1);
            canvas.drawCircle(startX, startY, 100, paint);

            startX++;
            startY++;

            holder.unlockCanvasAndPost(canvas);
        }
    }

    void pause() {
        isPlay = false;
        try {
            thread.join();
        } catch (Exception e) {}

    }

    void resume() {
        isPlay = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while(isPlay) {
            draw();
        }
    }
}
