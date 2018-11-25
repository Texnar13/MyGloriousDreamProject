package com.texnar13.games.mygloriousdream.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

import com.texnar13.games.mygloriousdream.gameObjects.GameObject;

public class Player implements GameObject {// todo ввести основные логи (начало/конец потока например)

    // ----- картинка игрока -----
    private Bitmap playerBitmap;
    // кадры
    private Rect[][] framesCoordinates;
    // состояние спрайта игрока (0 - на нас, 1 - в право, 2 - от нас, 3 - в лево, 4 - спец)(для графики)
    private int state = 0;
    // время на кадр
    int frameTimeMillis = 125;//8 кадров в секунду
    // номер текущего кадра
    private int currentFrame = 0;

    // поток вычислений
    private PlayerThread playerThread;
    private boolean isRunning = true;
    // масса игрока
    private int weight = 70;
    // координаты в которых находится игрок
    private PointF position = new PointF(0, 0);// единица измерения - 1/100 метра
    // скорость игрока по осям
    private PointF velocity = new PointF(0, 0);// единица измерения - метры/секунды
    // ускорение игрока по осям
    private PointF acceleration = new PointF(0, 0);// единица измерения - метры/(секунды*секунды)
    // сила действующая на игрока
    private PointF force = new PointF(0, 0);

    boolean isLog = false;


    public Player(Bitmap playerBitmap, Rect[][] framesCoordinates, long positionX, long positionY) {
        this.playerBitmap = playerBitmap;
        this.framesCoordinates = framesCoordinates;
        this.position.x = positionX;
        this.position.y = positionY;
        // запускаем поток расчетов
        playerThread = new PlayerThread();
        playerThread.start();
        playerThread.setName("playerThread");
    }

    // ----- графика -----
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(false);
        paint.setSubpixelText(false);

        // положение кадра на полотне
        Rect frameCoordinates = new Rect();
        frameCoordinates.set(
                (int) (position.x / 100),
                (int) (position.y / 100),
                (int) (position.x / 100 + (framesCoordinates[state][currentFrame].right - framesCoordinates[state][currentFrame].left) * 5),
                (int) (position.y / 100 + (framesCoordinates[state][currentFrame].bottom - framesCoordinates[state][currentFrame].top) * 5)
        );
        // рисуем кадр
        canvas.drawBitmap(
                playerBitmap,
                framesCoordinates[state][currentFrame],
                frameCoordinates,
                paint);
    }

    private void calculateCurrentPositionAndVelocityByLastAccelerationVelocityAndTime(long timeMillis) {
        // движение с постоянным рывком
        float time = timeMillis / 1000F;
        //трение   разложение трения по векторам
        //0.3 - коэфф трения

//        force.x = (float) (force.x - (weight * 10 * 0.3));
//        if (force.x < 0) {
//            force.x = 0;
//        }
//        force.y = (float) (force.y - (weight * 10 * 0.3));
//        if (force.y < 0) {
//            force.y = 0;
//        }
        // расчитываем путь по начальной скорости и ускорению
        position.x = position.x + ((velocity.x * time + acceleration.x * time * time / 2 + force.x * time * time * time / 6) * 100);
        position.y = position.y + ((velocity.y * time + acceleration.y * time * time / 2 + force.y * time * time * time / 6) * 100);
        // расчитываем конечную скорость
        velocity.x = velocity.x + acceleration.x * time + force.x * time * time / 2;
        velocity.y = velocity.y + acceleration.y * time + force.y * time * time / 2;
        // ускорение
        acceleration.x = acceleration.x + force.x * time;
        acceleration.y = acceleration.y + force.y * time;
    }


    //addТрение( массса, суммаСил){
    // }


// ----- поток расчетов игрока -----

    private class PlayerThread extends Thread {// todo изображение в некоторые моменты растягивается по X, сглаживание, пиксель к пикселю экран-canvas, настроить для фона промежутки отображения

        @Override
        public void run() {
            Log.i("MyGloriousDream:Player", "PlayerThread -> run()");
            /// ---- инициализация ----
            // время прошлого цикла (для физики)
            long lastSeries = System.currentTimeMillis();
            // время отображения прошлого кадра (для графики)
            long lastFrameTime = lastSeries;
            while (isRunning) {
                /// ---- цикл ----
                // считываем текущее время
                long nowTime = System.currentTimeMillis();
                // ------ расчитываем физику ------
                // --- позиция и скорость ---
                if(nowTime - lastSeries>=1) {
                    calculateCurrentPositionAndVelocityByLastAccelerationVelocityAndTime(nowTime - lastSeries);
                }
                // --- закончили работать со временем, оно стало прошлым ---
                lastSeries = nowTime;
                // ------ обновляем состояние кадра ------
                // --- тип кадра ---
                if (Math.abs(velocity.y) >= Math.abs(velocity.x)) {// если по Y он движется быстрее (по модулю)
                    if (velocity.y >= 0) {// движется в положительном направлении по Y или стоит
                        state = 0;
                    } else {// в отрицательном
                        state = 2;
                    }
                } else {// по X
                    if (velocity.x >= 0) {// движется в положительном направлении по X
                        state = 1;
                    } else {// в отрицательном
                        state = 3;
                    }
                }



                // --- номер кадра ---
                if (velocity.x == 0 && velocity.y == 0) {// игрок стоит
                    // в этом случае кадр один и тот же
                    currentFrame = 0;// пробежка 0->0
                    // в этот момент отработал кадр и стал прошлым
                    lastFrameTime = nowTime;
                } else {// игрок движется
                    // сколько кадров должно было быть показано за прошедшее время
                    int count = (int) ((nowTime - lastFrameTime) / frameTimeMillis);
                    // считаем номер кадра
                    currentFrame = ((currentFrame + count - 1) % (framesCoordinates[state].length - 1)) + 1;// пробежка 1->4
                    // в этот момент отработал кадр и стал прошлым
                    if (count != 0) lastFrameTime = nowTime;
                }

            }
        }
    }

    public void finishThread() {
        Log.i("MyGloriousDream:Player", "PlayerThread -> finishThread()");

        isRunning = false;
        boolean retry = true;
        // пока поток не завершится метод работает
        while (retry) {
            try {
                playerThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //
            }
        }
    }


// ------ команды сверху ------

    public void setForce(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }

    public float[] getInfo() {
        return new float[]{position.x, position.y,
                velocity.x, velocity.y,
                acceleration.x, acceleration.y,
                force.x, force.y
        };
    }

}





