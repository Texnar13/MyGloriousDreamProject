package com.texnar13.games.mygloriousdream;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // создаем компонент
        CanvView gameView = new CanvView(this);
        // создаем движок
        MyEngine engine = new MyEngine(gameView);

        // выводим
        setContentView(gameView);
        // обрабатываем нажатия
        gameView.setOnTouchListener(this);


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()& MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }



        // обработали нажатие
        return true;
    }
}
