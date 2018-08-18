package com.texnar13.games.mygloriousdream.myPac;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class Sprite {

    //глаз
    Bitmap bitmap;
    private List<Rect> frames;
    private int frameWidth;
    private int frameHeight;
    private int currentFrame;
    private double frameTime;
    private double timeForCurrentFrame;
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    private int padding;


    public Sprite(double x, double y, double velocityX, double velocityY, Rect initialFrame, Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.bitmap = bitmap;
        this.frames = new ArrayList<>();
        this.frames.add(initialFrame);
        this.timeForCurrentFrame = 0.0;
        this.frameTime = 0.1;
        this.currentFrame = 0;
        this.frameWidth = initialFrame.width();
        this.frameHeight = initialFrame.height();
        this.padding = 20;
    }

    // метод добавления кадров
    public void addFrame(Rect frame) {
        frames.add(frame);
    }

    // обновление внутреннего состояния спрайта
    public void update(int ms) {
        timeForCurrentFrame += ms;
        if (timeForCurrentFrame >= frameTime) {
            currentFrame = (currentFrame + 1) % frames.size();
            timeForCurrentFrame = timeForCurrentFrame - frameTime;
        }
        x = x + velocityX * ms / 1000.0;
        y = y + velocityY * ms / 1000.0;
    }

    public void draw(Canvas canvas) {
        Paint p = new Paint();
        Rect destination = new Rect((int) x, (int) y, (int) (x + frameWidth), (int) (y + frameHeight));
        canvas.drawBitmap(bitmap, frames.get(currentFrame), destination, p);
    }

}
