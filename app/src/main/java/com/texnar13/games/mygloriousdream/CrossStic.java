package com.texnar13.games.mygloriousdream;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;

public class CrossStic {// крестовина
    //0_______
    //|  ||  |
    //|<-  ->|
    //|  ||  |
    // ----- константы -----
    static final int BUTTON_SIZE = 40;

    // -- смещение стика по осям --
    float offsetX = 0;
    float offsetY = 0;
    // контейнер стика
    Rect stickContainer;
    // контейнер кнопки влево
    Rect leftButtonContainer;
    // контейнер кнопки вверх
    Rect upButtonContainer;
    // контейнер кнопки вправо
    Rect rightButtonContainer;
    // контейнер кнопки вниз
    Rect downButtonContainer;
    // контейнер кнопки центр
    Rect centerButtonContainer;

    // ----- создание экземпляра -----
    public CrossStic(int marginX, int marginY) {
        stickContainer = new Rect(
                marginX,
                marginY,
                marginX + BUTTON_SIZE * 3,
                marginY + BUTTON_SIZE * 3
        );
    }

    Rect draw(Canvas canvas) {

        return stickContainer;
    }

    PointF getOffset() {
        PointF offset = new PointF();
        offset.set(offsetX, offsetY);
        return offset;
    }

    void pressAndReturnOffset(int pressedX, int pressedY) {

    }
}
