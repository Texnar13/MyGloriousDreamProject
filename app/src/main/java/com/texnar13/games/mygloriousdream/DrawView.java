package com.texnar13.games.mygloriousdream;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

// поверхность поддерживающая SurfaceHolder
public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;

    public DrawView(Context context) {
        super(context);
        // ставим этой поверхности этот обработчик
        getHolder().addCallback(this);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // ставим этой поверхности этот обработчик
        getHolder().addCallback(this);
    }

    // запускаем поток графики и передаем ему данные для работы с графикой
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(), getHolder());
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // останавливаем графику и ждем когда поток завершится
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.requestStop();
        boolean retry = true;
        // пока поток не завершится метод работает
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //
            }
        }
    }

    public DrawThread getDrawThread() {
        return this.drawThread;
    }
}
