package com.texnar13.games.mygloriousdream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.TextView;

import com.texnar13.games.mygloriousdream.gameObjects.CrossStic;
import com.texnar13.games.mygloriousdream.gameObjects.Flat;
import com.texnar13.games.mygloriousdream.gameObjects.Player;

public class DrawThread extends Thread {


    public Handler controllerHandler;
    // поверхность для рисования
    private SurfaceHolder surfaceHolder;
    // работает ли поток
    private volatile boolean isRunning = true;


// ---- игровые обьекты ----

    // игровое поле
    private Flat flat;
    // игрок
    private Player player;
    // виртуальный стик
    CrossStic stic;

    // отрисовываемая очередь (односвязный список обьектов с интерфейсом рисуемое/потомками gameObject ) еще задать приоритеты отрисовки

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
        player = new Player(
                Bitmap.createBitmap(playerForwardImage, 0, 0, playerForwardImage.getWidth(), playerForwardImage.getHeight(), null, false),
                playerFramesCoordinates, 1000, 1000);

        // --- создаем крестовину ---
        stic = new CrossStic(50, 1200);

    }

// -------- поток --------

    @Override
    public void run() {
        // время на кадр
        int frameTimeMillis = 125;//8 кадров в секунду
        // время прошлого кадра
        long lastFrameTime = System.currentTimeMillis();
        // сейчас
        long nowTime;


        // -------- поток графики --------
        while (isRunning) {
            // текущее время
            nowTime = System.currentTimeMillis();
            // перерыв между кадрами
            if (nowTime - lastFrameTime > frameTimeMillis) {
                lastFrameTime = nowTime;
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    try {
                        // -- рисуем --
                        // поле
                        if (flat != null)
                            flat.draw(canvas);
                        // игрок
                        if (player != null) {
                            player.draw(canvas);
//                        float t[] = player.getInfo();
//                        Log.e("GloriousDream","x:" + t[0] +
//                                    "\ny:" + t[1] +
//                                    "\nvX:" + t[2] +
//                                    "\nvY:" + t[3] +
//                                    "\naX:" + t[4] +
//                                    "\naY:" + t[5] +
//                                    "\nfX:" + t[6] +
//                                    "\nfY:" + t[7]);

//                        if (controllerHandler != null) {
//                            float t[] = player.getInfo();
//                            Message message = controllerHandler.obtainMessage();
//                            message.what = 0;
//                            message.obj = "x:" + t[0] +
//                                    "\ny:" + t[1] +
//                                    "\nvX:" + t[1] +
//                                    "\nvY:" + t[1] +
//                                    "\naX:" + t[1] +
//                                    "\naY:" + t[1] +
//                                    "\nfX:" + t[1] +
//                                    "\nfY:" + t[1];
//                            controllerHandler.handleMessage(message);
//                        }
                        }
                        // ---- вывод кнопок ----
                        // -- крестовина --
                        if (stic != null)
                            stic.draw(canvas);


                    } finally {
                        // выводим кадр
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
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


    public void press(float x, float y) {
        //player.setForce(1);
        if (stic != null) {
            PointF pSticIncline = stic.pressAndReturnIncline(new PointF(x, y));
            Log.e("MyGloriousDream", "press: pSticIncline.x=" + pSticIncline.x + " pSticIncline.y=" + pSticIncline.y);
            pSticIncline.x = pSticIncline.x * 200;
            pSticIncline.y = pSticIncline.y * 200;
            player.setForce(pSticIncline.x, pSticIncline.y);
        }

    }

    public void release(float x, float y) {
        if (stic != null) {
            stic.release(new PointF(x, y));

            player.setForce(0,0);
        }
    }
}