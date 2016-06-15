package com.example.kristoffermichelsen.reallasersurvival;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.gesture.Gesture;
import android.graphics.Color;
import android.graphics.Point;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class GameActivity2 extends AppCompatActivity implements GestureDetector.OnGestureListener {

    GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        detector = new GestureDetector(this,this);
        GridLayout grid = (GridLayout) findViewById(R.id.gameScreen2);
        grid.setUseDefaultMargins(false);
        Display display = getWindowManager().getDefaultDisplay();
        Point size= new Point();
        display.getSize(size);
        int screenWidth=size.x;
        int screenHeight=size.y;

        int count=0;

        for(int i=0;i<13;i++){

            for(int j=0;j<9;j++){

                ImageView newPos = new ImageView(getApplicationContext());

                if(count%9 == 0) {
                    newPos.setBackgroundColor(Color.WHITE);
                } else if (count%9 == 1) {
                    newPos.setBackgroundColor(Color.LTGRAY);
                } else if (count%9 == 2) {
                    newPos.setBackgroundColor(Color.DKGRAY);
                } else if (count%9 == 3) {
                    newPos.setBackgroundColor(Color.RED);
                } else if (count%9 == 4) {
                    newPos.setBackgroundColor(Color.GREEN);
                } else if (count%9 == 5) {
                    newPos.setBackgroundColor(Color.BLUE);
                } else if (count%9 == 6) {
                    newPos.setBackgroundColor(Color.YELLOW);
                } else if (count%9 == 7) {
                    newPos.setBackgroundColor(Color.MAGENTA);
                } else if (count%9 == 8) {
                    newPos.setBackgroundColor(Color.CYAN);
                }

                if(i==0 || i==12){
                    newPos.setMinimumHeight(screenHeight/24);
                }else{
                    newPos.setMinimumHeight(screenHeight/12);

                }

                if(j==0 || j==8){
                    newPos.setMinimumWidth(screenWidth/16);
                }
                else{
                    newPos.setMinimumWidth(screenWidth/8);

                }
                count++;


                grid.addView(newPos);

            }}

        //test

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float vel1, float vel2){
        //ImageView userBall= (ImageView) findViewById(R.id.userBall);
        //userBall.layout(2,3,1,2);

        float deltaX =Math.abs(event1.getX()-event2.getX());
        float deltaY = Math.abs(event1.getY()-event2.getY());

        if(event1.getX()<event2.getX() && deltaX>100){
            //move right
            //userBall.setBackgroundColor(Color.GREEN);

        } else if(event1.getX()>event2.getX() && deltaX>100){
            // move left
            //userBall.setBackgroundColor(Color.BLUE);

        }
        if(event1.getY()<event2.getY() && deltaY>100){
            //move down
            // userBall.setBackgroundColor(Color.RED);

        } else if(event1.getY()>event2.getY() && deltaY>100){
            //move up
            // userBall.setBackgroundColor(Color.YELLOW);


        }


        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public void onLongPress(MotionEvent e) {
        //ImageView userBall= (ImageView) findViewById(R.id.userBall);
        GridLayout grid = (GridLayout) findViewById(R.id.gameScreen);
        //grid.getChildAt(16).setBackgroundColor(Color.GREEN);



        //grid.addView(newPos);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }
}






