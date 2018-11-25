package com.texnar13.games.mygloriousdream.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.texnar13.games.mygloriousdream.gameObjects.GameObject;

public class Flat implements GameObject {

    private Bitmap backgroundBitmap;


    public Flat(Bitmap backgroundBitmap) {
        this.backgroundBitmap = backgroundBitmap;
    }

    // ------ метод отрисовки графики ------

    @Override
    public void draw(Canvas canvas) {
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
