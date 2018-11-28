package com.texnar13.games.mygloriousdream.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.texnar13.games.mygloriousdream.gameObjects.GameObject;

public class Flat implements GameObject {

    //размер клетки
    int cellSize = 32*5;//px

    // массив с картой
    Biome biomes[][] = new Biome[][]{
            {new Biome()},
            {new Biome()}
    };


    private Bitmap backgroundBitmap;

    public Flat(Bitmap backgroundBitmap) {
        this.backgroundBitmap = backgroundBitmap;
    }

    // ------ метод отрисовки графики ------

    @Override
    public void draw(Canvas canvas) {
//        // рисуем
//        Paint p = new Paint();
//        p.setStyle(Paint.Style.FILL_AND_STROKE);
//        for (int j = 0; j < canvas.getWidth() / backgroundBitmap.getWidth() + 1; j++) {
//            for (int i = 0; i < canvas.getHeight() / backgroundBitmap.getHeight() + 1; i++) {
//                canvas.drawBitmap(
//                        backgroundBitmap,
//                        backgroundBitmap.getHeight() * j,
//                        backgroundBitmap.getHeight() * i,
//                        p
//                );
//            }
//        }


        // ------ отрисовка карты ------
        Paint p = new Paint();

        // отступы от края
        int xMargin = 0;
        int yMargin = 0;
        // по карте
        for (int i = 0; i < biomes.length; i++) {
            for (int j = 0; j < biomes[i].length; j++) {

                // ------ отрисовка биома ------
                for (int k = 0; k < biomes[i][j].map.length; k++) {
                    for (int l = 0; l < biomes[i][j].map[k].length; l++) {

                        // тип блока
                        switch (biomes[i][j].map[k][l]) {
                            case Biome.BLOCK_AIR:

                                break;
                            case Biome.BLOCK_DIRT:
                                canvas.drawBitmap(backgroundBitmap,
                                        new Rect(0, 0, 32, 32),// координаты на ресурсе
                                        new Rect(xMargin + cellSize * l, yMargin + cellSize * k, xMargin + cellSize * l + cellSize, yMargin + cellSize * k + cellSize),// на холсте
                                        p
                                );
                                break;
                        }


                    }
                }
                // прибавляем биом по x ->
                xMargin = xMargin + biomes[i][j].map[0].length * cellSize;
            }
            // сбрасываем биом по x тк отработали ряд
            xMargin = 0;
            // увеличиваем по y
            yMargin = yMargin + biomes[i][0].map.length * cellSize;
        }
    }
}


