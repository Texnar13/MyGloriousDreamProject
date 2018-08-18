package com.texnar13.games.mygloriousdream;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Flat implements GameObject {

    private Bitmap backgroundBitmap;

    // интервал времени между отрисовками
    long breaksBetweenTheFrames = 10;
    // время когда был отрисован прошлый кадр
    long lastFrameTime;

    public Flat(Bitmap backgroundBitmap) {
        this.backgroundBitmap = backgroundBitmap;
        lastFrameTime = System.currentTimeMillis();
    }

    // ------ метод отрисовки графики ------

    @Override
    public void draw(Canvas canvas) {
        // текущее время
        long nowTime = System.currentTimeMillis();
        // можем ли уже отрисовывать кадр (для снижения нагрузки на процессор)
        if (nowTime - lastFrameTime > breaksBetweenTheFrames) {
            // теперь этот кадр прошлый
            lastFrameTime = nowTime;
            // рисуем
            Paint p = new Paint();
            p.setStyle(Paint.Style.FILL_AND_STROKE);
            for (int j = 0; j < canvas.getWidth() / backgroundBitmap.getWidth() + 1; j++) {
                for (int i = 0; i < canvas.getHeight() / backgroundBitmap.getHeight() + 1; i++) {
                    canvas.drawBitmap(
                            backgroundBitmap,
                            backgroundBitmap.getHeight() * j,
                            backgroundBitmap.getHeight() * i,
                            p
                    );
                }
            }
        }
    }
}
