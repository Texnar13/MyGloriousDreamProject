package com.texnar13.games.mygloriousdream;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Player implements GameObject {// todo ввести основные логи (начало/конец потока например)

    // ----- картинка игрока -----
    private Bitmap playerBitmap;
    // кадры
    private Rect[][] framesCoordinates;
    // состояние спрайта игрока (0 - на нас, 1 - в право, 2 - от нас, 3 - в лево, 4 - спец)(для графики)
    private int state = 0;
    // время на кадр
    int frameTimeMillis = 125;
    // номер текущего кадра
    private int currentFrame = 0;

    // поток вычислений
    private PlayerThread playerThread;
    private boolean isRunning = true;
    // координаты в которых находится игрок
    private float positionX;// единица измерения - 1/100 метра
    private float positionY;
    // скорость игрока по осям
    private float velocityX = 0;// единица измерения - метры/секунды
    private float velocityY = 0;
    // ускорение игрока по осям
    private float accelerationX = 1;// единица измерения - метры/(секунды*секунды)
    private float accelerationY = 0;


    Player(Bitmap playerBitmap, Rect[][] framesCoordinates, long positionX, long positionY) {
        this.playerBitmap = playerBitmap;
        this.framesCoordinates = framesCoordinates;
        this.positionX = positionX;
        this.positionY = positionY;
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
                (int) (positionX / 100),
                (int) (positionY / 100),
                (int) (positionX / 100 + (framesCoordinates[state][currentFrame].right - framesCoordinates[state][currentFrame].left) * 5),
                (int) (positionY / 100 + (framesCoordinates[state][currentFrame].bottom - framesCoordinates[state][currentFrame].top) * 5)
        );
        // рисуем кадр
        canvas.drawBitmap(
                playerBitmap,
                framesCoordinates[state][currentFrame],
                frameCoordinates,
                paint);
    }

    //    private void calculatePositionByVelosityAndTime(long timeMillis) {
    //        positionX = positionX + (long) (timeMillis * velocityX / 100000);
    //        positionY = positionY + (long) (timeMillis * velocityY / 100000);
    //    }
    private void calculateCurrentPositionAndVelocityByLastAccelerationVelocityAndTime(long timeMillis) {
        // расчитываем путь по начальной скорости и ускорению
        positionX = positionX + ((velocityX * timeMillis / 1000 + accelerationX * timeMillis / 1000 * timeMillis / 1000 / 2) * 100);
        positionY = positionY + ((velocityY * timeMillis / 1000 + accelerationY * timeMillis / 1000 * timeMillis / 1000 / 2) * 100);
        // расчитываем конечную скорость
        velocityX = velocityX + accelerationX * timeMillis / 1000;
        velocityY = velocityY + accelerationY * timeMillis / 1000;
    }

    //addТрение( массса, суммаСил){
    // }


    // ----- поток расчетов игрока -----
    private class PlayerThread extends Thread {// todo изображение в некоторые моменты растягивается по X, сглаживание, пиксель к пикселю экран-canvas, неправильные координаты кадров в изображении, настроить для фона промежутки отображения
        @Override
        public void run() {
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
                calculateCurrentPositionAndVelocityByLastAccelerationVelocityAndTime(nowTime - lastSeries);

                // --- закончили работать со временем, оно стало прошлым ---
                lastSeries = nowTime;
                // ------ обновляем состояние кадра ------
                // --- тип кадра ---
                if (Math.abs(velocityY) >= Math.abs(velocityX)) {// если по Y он движется быстрее (по модулю)
                    if (velocityY >= 0) {// движется в положительном направлении по Y или стоит
                        state = 0;
                    } else {// в отрицательном
                        state = 2;
                    }
                } else {// по X
                    if (velocityY >= 0) {// движется в положительном направлении по X
                        state = 1;
                    } else {// в отрицательном
                        state = 3;
                    }
                }
                // --- номер кадра ---
                if (velocityX == 0 && velocityY == 0) {// игрок стоит
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
}





