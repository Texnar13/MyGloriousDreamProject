package com.texnar13.games.mygloriousdream.gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class CrossStic {// крестовина
    //0_______
    //|  ||  |
    //|<-  ->|
    //|  ||  |
    // ----- константы -----
    static final int BUTTON_SIZE = 300;

    // -- смещение стика по осям --
    float offsetX = 0;
    float offsetY = 0;
    // контейнер стика
    private RectF stickContainer;
    // контейнер кнопки влево
    private RectF leftButtonContainer;
    // контейнер кнопки вверх
    private RectF upButtonContainer;
    // контейнер кнопки вправо
    private RectF rightButtonContainer;
    // контейнер кнопки вниз
    private RectF downButtonContainer;
    // контейнер кнопки центр
    private RectF centerButtonContainer;


    // ----- создание экземпляра -----
    public CrossStic(int marginX, int marginY) {
        stickContainer = new RectF(
                marginX,
                marginY,
                marginX + BUTTON_SIZE * 3,
                marginY + BUTTON_SIZE * 3
        );
        // контейнер кнопки влево
        leftButtonContainer = new RectF(
                marginX,
                marginY + BUTTON_SIZE,
                marginX + BUTTON_SIZE,
                marginY + 2 * BUTTON_SIZE
        );
        // контейнер кнопки вверх
        upButtonContainer = new RectF(
                marginX + BUTTON_SIZE,
                marginY,
                marginX + 2 * BUTTON_SIZE,
                marginY + BUTTON_SIZE
        );
        // контейнер кнопки вправо
        rightButtonContainer = new RectF(
                marginX + 2 * BUTTON_SIZE,
                marginY + BUTTON_SIZE,
                marginX + BUTTON_SIZE * 3,
                marginY + 2 * BUTTON_SIZE
        );
        // контейнер кнопки вниз
        downButtonContainer = new RectF(
                marginX + BUTTON_SIZE,
                marginY + 2 * BUTTON_SIZE,
                marginX + 2 * BUTTON_SIZE,
                marginY + BUTTON_SIZE * 3
        );
        // контейнер кнопки центр
        centerButtonContainer = new RectF(
                marginX + BUTTON_SIZE,
                marginY + BUTTON_SIZE,
                marginX + 2 * BUTTON_SIZE,
                marginY + 2 * BUTTON_SIZE
        );

    }

    public RectF draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.GRAY);
        //paint.setAlpha();

        // -- рисуем --
        canvas.drawRect(leftButtonContainer, paint);
        canvas.drawRect(upButtonContainer, paint);
        canvas.drawRect(rightButtonContainer, paint);
        canvas.drawRect(downButtonContainer, paint);
        canvas.drawRect(centerButtonContainer, paint);
        //canvas.drawRect(1,1,1000,1000,paint);

        return stickContainer;
    }

    PointF getOffset() {
        PointF offset = new PointF();
        offset.set(offsetX, offsetY);
        return offset;
    }

    public PointF pressAndReturnIncline(PointF point) {
        // не стик
        if (!isPointInsideRect(point,stickContainer)) {
            return new PointF(0, 0);
        }
        // контейнер кнопки влево
        if (isPointInsideRect(point,leftButtonContainer)) {
            Log.e("MyGloriousDream", "press:left");
            return new PointF(-1, 0);
        }
        // контейнер кнопки вверх
        if (isPointInsideRect(point,upButtonContainer)) {
            Log.e("MyGloriousDream", "press:up");
            return new PointF(0, -1);
        }
        // контейнер кнопки вправо
        if (isPointInsideRect(point,rightButtonContainer)) {
            Log.e("MyGloriousDream", "press:right");
            return new PointF(1, 0);
        }
        // контейнер кнопки вниз
        if (isPointInsideRect(point,downButtonContainer)) {
            Log.e("MyGloriousDream", "press:down");
            return new PointF(0, 1);
        }
        // контейнер кнопки центр
        if (isPointInsideRect(point,centerButtonContainer)) {
            return new PointF(0, 0);
        }

        return new PointF(0, 0);
    }

    // проверка на принадлежность точки прямоугольнику
    private boolean isPointInsideRect(PointF point, RectF rect){
        return (point.x >= rect.left &&
                point.x <= rect.right &&
                point.y >= rect.top &&
                point.y <= rect.bottom);
    }

    public void release(PointF pointF) {

    }
}
