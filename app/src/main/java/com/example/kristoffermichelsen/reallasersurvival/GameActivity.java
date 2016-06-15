package com.example.kristoffermichelsen.reallasersurvival;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;


public class GameActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        GridLayout grid = (GridLayout) findViewById(R.id.gameScreen);
        grid.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()));


    }




}



