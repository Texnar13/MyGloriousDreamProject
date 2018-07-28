package com.texnar13.games.mygloriousdream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class CanvView extends View {

    //глаз
    Bitmap eye;


    public CanvView(Context context) {
        super(context);
    }


    //вывод графики
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setStyle(Paint.Style.FILL);
        canvas.drawColor(Color.GRAY);
        canvas.drawRect(100,100,200,200,p);
    }
}
