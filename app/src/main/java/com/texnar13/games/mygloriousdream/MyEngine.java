package com.texnar13.games.mygloriousdream;

import android.os.CountDownTimer;
import android.util.Log;

public class MyEngine {

    private final long timeForFrame = 30;//ms
    private long now;
    // время прошлого измерения
    private long last;

    // вывод графики
    private CanvView gameView;

    //

    // поток подсчетов
    private GameThread thread;
    // идет ли поток
    private boolean flag;


    // вычисляе текущее время
    // считаем координаты, загружаем, итд
    // если время еще осталось выводим графику иначе переходим к следующему кадру
    //


    // конструктор
    public MyEngine(CanvView gameView) {
        this.gameView = gameView;
        // считаем стартовое время
        last = System.currentTimeMillis();
        // запускаем поток расчетов
        thread = new GameThread();
        thread.run();
        flag = true;
    }


    // цикл вывода
    void cicle() {
        //находим текущее время
        long now = System.currentTimeMillis();
        // если еще не слишком поздно, то считаем и выводим
        if (now - last <= timeForFrame) {
            Log.e("ddddd", "t" + (now - last));

        }
        last = System.currentTimeMillis();
    }


//    class Timer extends CountDownTimer {
//        public Timer() {
//            super(Integer.MAX_VALUE, timeForFrame);
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//            update();
//        }
//
//        @Override
//        public void onFinish() {
//        }
//    }

    class GameThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (flag) {
                cicle();
            }

        }
    }
}
