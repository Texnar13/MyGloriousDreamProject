package com.texnar13.games.mygloriousdream;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    // ----- элементы управления -----
    DrawView drawView;

    Button buttonTopLeft;
    Button buttonTopCenter;
    Button buttonTopRight;
    Button buttonCenterLeft;
    Button buttonCenterCenter;
    Button buttonCenterRight;
    Button buttonBottomLeft;
    Button buttonBottomCenter;
    Button buttonBottomRight;

    // ----- игровые обьекты -----
    Flat flat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //drawView = new DrawView(this);
        //setContentView(drawView);
        setContentView(R.layout.activity_main);

        // --- ставим обработчики касаний ---
        drawView = (DrawView) findViewById(R.id.activity_main_draw_view);

        buttonTopLeft = (Button) findViewById(R.id.activity_main_button_left_top);
        buttonTopLeft.setOnClickListener(this);
        buttonTopCenter = (Button) findViewById(R.id.activity_main_button_center_top);
        buttonTopCenter.setOnClickListener(this);
        buttonTopRight = (Button) findViewById(R.id.activity_main_button_right_top);
        buttonTopRight.setOnClickListener(this);
        buttonCenterLeft = (Button) findViewById(R.id.activity_main_button_left_center);
        buttonCenterLeft.setOnClickListener(this);
        buttonCenterCenter = (Button) findViewById(R.id.activity_main_button_center_center);
        buttonCenterCenter.setOnClickListener(this);
        buttonCenterRight = (Button) findViewById(R.id.activity_main_button_right_center);
        buttonCenterRight.setOnClickListener(this);
        buttonBottomLeft = (Button) findViewById(R.id.activity_main_button_left_bottom);
        buttonBottomLeft.setOnClickListener(this);
        buttonBottomCenter = (Button) findViewById(R.id.activity_main_button_center_bottom);
        buttonBottomCenter.setOnClickListener(this);
        buttonBottomRight = (Button) findViewById(R.id.activity_main_button_right_bottom);
        buttonBottomRight.setOnClickListener(this);

    }


    // считывание касаний игры
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_button_left_top:
                Toast.makeText(getApplicationContext(),"влево вверх",Toast.LENGTH_SHORT).show();

                break;
            case R.id.activity_main_button_center_top:
                break;
            case R.id.activity_main_button_right_top:
                break;
            case R.id.activity_main_button_left_center:
                break;
            case R.id.activity_main_button_center_center:
                break;
            case R.id.activity_main_button_right_center:
                break;
            case R.id.activity_main_button_left_bottom:
                break;
            case R.id.activity_main_button_center_bottom:
                break;
            case R.id.activity_main_button_right_bottom:
                break;
        }
    }
}

