package com.texnar13.games.mygloriousdream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.View;

public class CanvView extends View {

    //размеры view
    int viewWidth;
    int viewHeight;

    //глаз
    Sprite eyeSprite;

    private final int timerInterval = 30;

    public CanvView(Context context) {
        super(context);
        // --- инициализируем глаз ---
        //получаем картинку
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.eye_storm);
        // задаем положение первого кадра и размеры кадров
        int w = b.getWidth() / 2;
        int h = b.getHeight() / 2;
        Rect firstFrame = new Rect(0, 0, w, h);
        // инициализируем
        eyeSprite = new Sprite(10, 0, 10, 100, firstFrame, b);

        //добавляем положение остальных кадров
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (i == 2 && j == 3) {
                    continue;
                }
                eyeSprite.addFrame(new Rect(j * w, i * h, j * w + w, i * w + w));
            }
        }

        //периодический таймер
        Timer t = new Timer();
        t.start();
    }

    // когда элементу присваивают новые размеры
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

    // обновление экрана
    void update() {
        //обновление спрайта
        eyeSprite.update(timerInterval);
        // обновление экрана
        invalidate();
    }


    //вывод графики
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // цвет фона
        canvas.drawARGB(250, 127, 199, 255);
        // отрисовка спрайта
        eyeSprite.draw(canvas);

        // текст
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setTextSize(55.0f);
        p.setColor(Color.WHITE);
        canvas.drawText(0 + "", viewWidth - 100, 70, p);
    }

    class Timer extends CountDownTimer {
        public Timer() {
            super(Integer.MAX_VALUE, timerInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            update();
        }

        @Override
        public void onFinish() {
        }
    }
}
