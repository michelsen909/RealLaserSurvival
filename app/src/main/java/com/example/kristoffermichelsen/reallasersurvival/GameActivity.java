package com.example.kristoffermichelsen.reallasersurvival;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
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


public class GameActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    GestureDetector detector;

    double multiplier=1.0;
    int score=0;
    Point ball;
    ImageView allCells [] [] = new ImageView[12][9];
    int allColors [] [] = new int [12][9];
    ImageView edges []  = new ImageView[34];
    TextView multiplierText;
    TextView scoreText;
    int wait=1000;

    private final Handler lasers = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage){
            String location = inputMessage.getData().getString("message");

            if(location.substring(0,1).equals("S")){
                String[] splitString =location.split(",",3);
                int edgeNum = Integer.parseInt(splitString[2]);
                int laserColor;
                switch (splitString[1]){
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
                Point laserPoint = new Point();
                //Log.i("GameActivity",edges[edgeNum].getX()+"");
                for(int i=0;i<12;i++){
                    for(int j=0;j<9;j++){
                        if(allCells[i][j].equals(edges[edgeNum])){
                            laserPoint.x=j;
                            laserPoint.y=i;
                        }
                    }
                }

                if(edgeNum<14){
                    int x=edgeNum/2;
                    x++;
                    for(int i=1;i<11;i++){
                        allCells[i][x].setBackgroundColor(laserColor);
                    }

                }
                else{
                    int y=(edgeNum-14)/2;
                    y++;
                    for(int i=1;i<8;i++){
                        allCells[y][i].setBackgroundColor(laserColor);
                    }



                }




            }
                else if(!location.equals("reset")){
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


                    multiplierText.setText(multiplier+"x");
                    scoreText.setText(score+"");
        }
                else{

                    resetEdges();
                    resetEntireBoard();
        }


        }
    };


    private void resetEdges(){
        for (ImageView i:edges) {
            i.setBackgroundColor(Color.BLACK);
        }
    }

    private void resetEntireBoard(){
        for(int i=0;i<12;i++){
            for(int j=0;j<9;j++){

                    allCells[i][j].setBackgroundColor(allColors[i][j]);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        detector = new GestureDetector(this,this);
        GridLayout grid = (GridLayout) findViewById(R.id.gameScreen2);
        multiplierText= (TextView) findViewById(R.id.multiplier);
        scoreText= (TextView) findViewById(R.id.score);

        scoreText.setText(""+0);
        multiplierText.setText(1.0+"x");

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
                    int color1 = Color.BLACK;
                    newPos.setBackgroundColor(color1);
                    allColors[i][j]=color1;
                } else if (count%2 == 1) {
                    int color2=Color.BLACK;
                    newPos.setBackgroundColor(color2);
                    allColors[i][j]=color2;
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



        allCells[6][4].setBackgroundColor(Color.WHITE);
        allCells[6][4].setBackgroundResource(R.drawable.ball);
        //test

        new Thread(new Runnable() {
            boolean dead=false;
             // multiplier
            int wait=1500; // initial wait
            int blinks=10; // how many times the lasers blink
            int breakBetween=400; // break between spawns

            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                    while(!dead) {
                        Random r= new Random();
                        ArrayList<Integer> fieldsUsed=new ArrayList();
                        int lasersSpawned=r.nextInt(4)+3;
                        ArrayList<String> allLasers= new ArrayList<String>();
                        for(int i=0;i<lasersSpawned;i++){
                        int selectEdge = r.nextInt(34);
                        while(fieldsUsed.contains(selectEdge)){
                            Log.i("GameActiviy","new select edge");
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
                        allLasers.add(color+","+selectEdge);
                        }
                        int innerWait=wait;

                        Thread.sleep(breakBetween); // break between laser spawns
                        while(innerWait>0) {

                            //Thread.sleep(innerWait);

                            for (String i : allLasers) {
                                sendCommand(i);
                            }

                            Thread.sleep(innerWait/2);
                            if(innerWait>700){
                                innerWait=700;
                            }
                            sendResetCommand();
                            Thread.sleep(innerWait);
                            innerWait=innerWait-(wait/blinks);
                        }

                        //wait=wait-100;
                        incrementScore();
                        incrementMultiplier();
                        for(String i:allLasers){
                            sendCommand("Shoot,"+i);
                        }

                    }




                }catch(InterruptedException e){

                }
            }
        }).start();
    }

    public void incrementMultiplier(){
        double increments=0.1;
        multiplier=multiplier+increments;
        multiplier=multiplier*10;
        multiplier=Math.round(multiplier);
        multiplier=multiplier/10;
    }

    public void incrementScore (){

        double adder=100*multiplier;
        score=(int)(score+adder);
    }

    public void sendResetCommand(){
        Message msg = lasers.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("message","reset");
        msg.setData(bundle);
        lasers.sendMessage(msg);
    }

    public void sendCommand(String message){


            Message msg = lasers.obtainMessage();
            Bundle bundle = new Bundle();

            bundle.putString("message",message);
            msg.setData(bundle);
            lasers.sendMessage(msg);



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
                //allCells[ball.y][ball.x].setBackgroundColor(Color.BLACK);
                allCells[ball.y][ball.x].setBackgroundResource(0);
                ball.x=ball.x+1;
                //allCells[ball.y][ball.x].setBackgroundColor(Color.WHITE);
                allCells[ball.y][ball.x].setBackgroundResource(R.drawable.ball);
                moved=true;

            }
            //userBall.setBackgroundColor(Color.GREEN);

        } else if(event1.getX()>event2.getX() && deltaX>100 && deltaX>deltaY){
            // move left
            if(ball.x!=1){
                //allCells[ball.y][ball.x].setBackgroundColor(Color.BLACK);
                allCells[ball.y][ball.x].setBackgroundResource(0);
                ball.x=ball.x-1;
                //allCells[ball.y][ball.x].setBackgroundColor(Color.WHITE);
                allCells[ball.y][ball.x].setBackgroundResource(R.drawable.ball);
                moved=true;

            }

            //userBall.setBackgroundColor(Color.BLUE);

        }
        if(!moved){
            if(event1.getY()<event2.getY() && deltaY>100 && deltaY>=deltaX){
                //move down
                if(ball.y!=10){
                    //allCells[ball.y][ball.x].setBackgroundColor(Color.BLACK);
                    allCells[ball.y][ball.x].setBackgroundResource(0);
                    ball.y=ball.y+1;
                    //allCells[ball.y][ball.x].setBackgroundColor(Color.WHITE);
                    allCells[ball.y][ball.x].setBackgroundResource(R.drawable.ball);
                    moved=true;
                }
                // userBall.setBackgroundColor(Color.RED);

            } else if(event1.getY()>event2.getY() && deltaY>100 && deltaY>=deltaX) {
                //move up
                if (ball.y != 1) {
                    //allCells[ball.y][ball.x].setBackgroundColor(Color.BLACK);
                    allCells[ball.y][ball.x].setBackgroundResource(0);
                    ball.y = ball.y - 1;
                    //allCells[ball.y][ball.x].setBackgroundColor(Color.WHITE);
                    allCells[ball.y][ball.x].setBackgroundResource(R.drawable.ball);
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






