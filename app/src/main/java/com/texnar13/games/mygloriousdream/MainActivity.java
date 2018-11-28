package com.texnar13.games.mygloriousdream;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.texnar13.games.mygloriousdream.gameObjects.Flat;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    // ----- элементы управления -----
    DrawView drawView;

    TextView logText;
    Handler controllerHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(drawView);
        setContentView(R.layout.activity_main);//drawView = new DrawView(this);
        logText = (TextView) findViewById(R.id.logText);


        // --- ставим обработчики касаний ---
        drawView = (DrawView) findViewById(R.id.activity_main_draw_view);
        drawView.setOnTouchListener(this);

        // даем возможность игровому потоку обращаться сюда
        controllerHandler = new Handler() {


            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    if (logText != null)
                        logText.setText((String) msg.obj);
                }
            }
        };
//        drawView.getDrawThread().controllerHandler = controllerHandler;

    }


    // считывание касаний игры
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                //Toast.makeText(getApplicationContext(), "нажал, молодец!", Toast.LENGTH_SHORT).show();
                drawView.getDrawThread().press(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_UP:
                drawView.getDrawThread().release(event.getX(), event.getY());
                break;
        }
        // обработали нажатие
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}

