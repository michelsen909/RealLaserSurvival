package com.example.kristoffermichelsen.reallasersurvival;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.gesture.Gesture;
import android.graphics.Color;
import android.graphics.Point;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.speech.tts.TextToSpeech;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class GameActivity2 extends AppCompatActivity implements GestureDetector.OnGestureListener {

    GestureDetector detector;

    Point ball;
    ImageView allCells [] [] = new ImageView[12][9];
    ImageView edges []  = new ImageView[34];

    private final Handler lasers = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage){
            String location = inputMessage.getData().getString("message");
            if(!location.equals("reset")){
                String [] splitString = location.split(",");
                int laserColor;
                switch (splitString[0]){
                    case "RED":
                        laserColor=Color.RED;
                        break;
                    case "GREEN":
                        laserColor=Color.GREEN;
                        break;
                    case "BLUE":
                        laserColor=Color.BLUE;
                        break;
                    default:
                        laserColor=Color.WHITE;
                        break;

                }

                edges[Integer.parseInt(splitString[1])].setBackgroundColor(laserColor);
            }
            else{
                resetEdges();
            }


        }
    };


    private void resetEdges(){
        for (ImageView i:edges) {
            i.setBackgroundColor(Color.BLACK);
        }
    }

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
        ball = new Point(4,6);

        TextView multiplier = (TextView) findViewById(R.id.multiplier);
        TextView score = (TextView) findViewById(R.id.score);

        multiplier.setMinimumHeight(screenHeight/24);
        multiplier.setMaxHeight(screenHeight/24);
        //multiplier.setTextSize(screenHeight/24);
        score.setMinimumHeight(screenHeight/24);
        score.setMaxHeight(screenHeight/24);
        //score.setTextSize(screenHeight/24);
        score.setMinimumWidth(screenWidth/3+screenWidth/32);
        //score.setMaxWidth(screenWidth/3);

        int count=0;

        for(int i=0;i<12;i++){

            for(int j=0;j<9;j++){

                ImageView newPos = new ImageView(getApplicationContext());

                /*
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
                */

                if(count%2 == 0) {
                    newPos.setBackgroundColor(Color.BLACK);
                } else if (count%2 == 1) {
                    newPos.setBackgroundColor(Color.BLACK);
                }


                if(i==0 || i==11){
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
                allCells[i][j]=newPos;











            }}

        int indicator=0;
        for(int i=0;i<7;i++){
            edges[indicator]=allCells[0][i+1];
            indicator++;
            edges[indicator]=allCells[11][i+1];
            indicator++;
        }
        for(int i=0;i<10;i++){
            edges[indicator]=allCells[i+1][0];
            indicator++;
            edges[indicator]=allCells[i+1][8];
            indicator++;
        }


        allCells[6][4].setBackgroundColor(Color.BLUE);
        allCells[6][4].setBackgroundResource(R.drawable.ball);

        //allCells[6][4].setImageResource(R.);

        //test

        new Thread(new Runnable() {
            boolean dead=false;
            int wait=3001;
            @Override
            public void run() {
                try{
                    while(!dead) {
                        Thread.sleep(wait);

                        sendCommand(1);
                        Thread.sleep(wait);
                        sendCommand(2);

                        //wait=wait-100;
                    }




                }catch(InterruptedException e){

                }
            }
        }).start();
    }

    public void sendCommand(int message){
        if(message==1){
            Random r= new Random();

            int lasersSpawned=r.nextInt(6)+1;
            ArrayList<Integer> fieldsUsed=new ArrayList();

            for(int i=0;i<lasersSpawned;i++) {
                Message msg = lasers.obtainMessage();
                Bundle bundle = new Bundle();
                int selectEdge = r.nextInt(34);
                while(fieldsUsed.contains(selectEdge)){
                    selectEdge = r.nextInt(34);
                }
                fieldsUsed.add(selectEdge);
                int selectColor = r.nextInt(3);
                String color = "YELLOW";
                switch (selectColor) {
                    case 1:
                        color = "RED";
                        break;
                    case 2:
                        color = "BLUE";
                        break;
                    case 0:
                        color = "GREEN";
                        break;
                }
                bundle.putString("message", color +","+ selectEdge);
                msg.setData(bundle);
                lasers.sendMessage(msg);
            }}
        else{
            Message msg = lasers.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("message","reset");
            msg.setData(bundle);
            lasers.sendMessage(msg);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float vel1, float vel2){
        //ImageView userBall= (ImageView) findViewById(R.id.userBall);
        //userBall.layout(2,3,1,2);

        boolean moved=false;
        float deltaX =Math.abs(event1.getX()-event2.getX());
        float deltaY = Math.abs(event1.getY()-event2.getY());

        if(event1.getX()<event2.getX() && deltaX>100 && deltaX>deltaY){
            //move right
            if(ball.x!=7){
                allCells[ball.y][ball.x].setBackgroundColor(Color.BLACK);
                ball.x=ball.x+1;
                allCells[ball.y][ball.x].setBackgroundColor(Color.WHITE);
                moved=true;

            }
            //userBall.setBackgroundColor(Color.GREEN);

        } else if(event1.getX()>event2.getX() && deltaX>100 && deltaX>deltaY){
            // move left
            if(ball.x!=1){
                allCells[ball.y][ball.x].setBackgroundColor(Color.BLACK);
                ball.x=ball.x-1;
                allCells[ball.y][ball.x].setBackgroundColor(Color.WHITE);
                moved=true;

            }

            //userBall.setBackgroundColor(Color.BLUE);

        }
        if(!moved){
            if(event1.getY()<event2.getY() && deltaY>100 && deltaY>=deltaX){
                //move down
                if(ball.y!=10){
                    allCells[ball.y][ball.x].setBackgroundColor(Color.BLACK);
                    ball.y=ball.y+1;
                    allCells[ball.y][ball.x].setBackgroundColor(Color.WHITE);
                    moved=true;
                }
                // userBall.setBackgroundColor(Color.RED);

            } else if(event1.getY()>event2.getY() && deltaY>100 && deltaY>=deltaX) {
                //move up
                if (ball.y != 1) {
                    allCells[ball.y][ball.x].setBackgroundColor(Color.BLACK);
                    ball.y = ball.y - 1;
                    allCells[ball.y][ball.x].setBackgroundColor(Color.WHITE);
                    moved = true;
                }
                // userBall.setBackgroundColor(Color.YELLOW);

            }
        }


        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public void onLongPress(MotionEvent e) {
        //ImageView userBall= (ImageView) findViewById(R.id.userBall);
        GridLayout grid = (GridLayout) findViewById(R.id.gameScreen2);
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






