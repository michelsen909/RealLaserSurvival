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


public class GameActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        detector = new GestureDetector(this,this);
        GridLayout grid = (GridLayout) findViewById(R.id.gameScreen);
        Display display = getWindowManager().getDefaultDisplay();
        Point size= new Point();
        display.getSize(size);
        int screenWidth=size.x;
        int screenHeight=size.y;

        int count=0;

        int colorh= Color.WHITE;




        for(int i=0;i<12;i++){
            if(colorh==Color.WHITE){
                colorh=Color.BLACK;
            }else{
                colorh=Color.WHITE;
            }
            for(int j=0;j<8;j++){

            ImageView newPos = new ImageView(getApplicationContext());
            newPos.setBackgroundColor(colorh);
                Log.i("GameApp",""+screenWidth);
                if(count%3==1){
                    newPos.setBackgroundColor(Color.BLUE);
                }
                if(count%3==2){
                    newPos.setBackgroundColor(Color.GREEN);
                }
                count++;
                //GridLayout.LayoutParams lp = new GridLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT,GridLayout.LayoutParams.MATCH_PARENT);
                //lp.setMargins(1,1,1,1);
                //newPos.setLayoutParams(lp);

                if(i==0){
                    newPos.setMinimumHeight(screenHeight/24);
                }else{
                    newPos.setMinimumHeight(screenHeight/12);

                }

                if(j==0){
                    newPos.setMinimumWidth(screenWidth/16);
                }
                else{
                    newPos.setMinimumWidth(screenWidth/8);

                }



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






