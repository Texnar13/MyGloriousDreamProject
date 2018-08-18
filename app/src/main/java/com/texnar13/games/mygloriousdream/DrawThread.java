package com.texnar13.games.mygloriousdream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {

    // поверхность для рисования
    private SurfaceHolder surfaceHolder;
    // работает ли поток
    private volatile boolean isRunning = true;

// ---- игровые обьекты ----

    // игровое поле
    private Flat flat;
    // игрок
    private Player player;

    // конструктор
    DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        // --- ставим поле ---
        Bitmap flatBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.yellow_stones);
        flat = new Flat(flatBackground);
        // --- выводим игрока ---
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inScaled = false; todo все изображения приходят в разрешении в 4 раза выше
        Bitmap playerForwardImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero_try_all, options);
        Log.e("GloriousDream", "playerForwardImage" + flatBackground.getHeight());
        // кадры
        Rect[][] playerFramesCoordinates = new Rect[5][5];// 0 - стоит, 1-4 - движется
        for (int i = 5; i > 0; i--) {
            for (int j = 0; j < 5; j++) {
                playerFramesCoordinates[5 - i][j] = new Rect();
                playerFramesCoordinates[5 - i][j].set(
                        playerForwardImage.getWidth() / 5 * j,
                        playerForwardImage.getWidth() / 5 * (i - 1),
                        playerForwardImage.getWidth() / 5 * (j + 1),
                        playerForwardImage.getHeight() / 5 * i
                );
            }
        }
        player = new Player(playerForwardImage, playerFramesCoordinates, 0, 0);
    }

//    public void setFlat(Flat flat) {
//        this.flat = flat;
//    }
//
//    public void setPlayer(Player player) {
//        this.player = player;
//    }

    // поток
    @Override
    public void run() {
        while (isRunning) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    // -- рисуем --
                    // поле
                    if (flat != null)
                        flat.draw(canvas);
                    // игрок
                    if (player != null)
                        player.draw(canvas);
                    // -- вывод кнопок --

                } finally {
                    // выводим кадр
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
        // закончили выводить графику чистим данные
        if (player != null)
            player.finishThread();
    }


    // остановка run()
    public void requestStop() {
        isRunning = false;
    }
}
