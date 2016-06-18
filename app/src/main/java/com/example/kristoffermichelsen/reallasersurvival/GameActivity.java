package com.example.kristoffermichelsen.reallasersurvival;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
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
    Point [] tunnel = new Point[2];

    boolean alive=true;
    int lives=1;

    boolean usedBack=false;

    int wait=1000;
    Drawable ballDraw;
    static int recentGameScore=0;
    static int ballColor = SettingsActivity.savedColor;



    private final Handler lasers = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage){
            String location = inputMessage.getData().getString("message");
            Random r =new Random();

            switch(location.substring(0,1)){
                case "0": // shieldpower
                    int px = r.nextInt(7)+1;
                    int py= r.nextInt(10)+1;

                    while(allCells[py][px].getForeground()==ballDraw){
                        px=r.nextInt(7)+1;
                        py=r.nextInt(10)+1;
                    }
                    Drawable powerBall = (Drawable)getDrawable(R.drawable.power_up);
                    powerBall.setColorFilter(new PorterDuffColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY));
                    powerBall.setLevel(10);

                    allCells[py][px].setForeground(powerBall);

                    break;
                case "1":
                    int p1x = r.nextInt(7)+1;
                    int p1y= r.nextInt(10)+1;

                    while(allCells[p1y][p1x].getForeground()==ballDraw){
                        p1x=r.nextInt(7)+1;
                        p1y=r.nextInt(10)+1;
                    }

                    int p2x = r.nextInt(7)+1;
                    int p2y= r.nextInt(10)+1;

                    while(allCells[p2y][p2x].getForeground()==ballDraw && (p2y!=p1y || p2x!=p1x)){
                        p2x=r.nextInt(7)+1;
                        p2y=r.nextInt(10)+1;
                    }

                    tunnel[0]=new Point(p1x,p1y);
                    tunnel[1]=new Point(p2x,p2y);

                    Drawable tunnel1 = (Drawable) getDrawable(R.drawable.power_up);
                    tunnel1.setColorFilter(new PorterDuffColorFilter(Color.MAGENTA, PorterDuff.Mode.MULTIPLY));
                    tunnel1.setLevel(11);

                    Drawable tunnel2 = (Drawable) getDrawable(R.drawable.power_up);
                    tunnel2.setColorFilter(new PorterDuffColorFilter(Color.MAGENTA, PorterDuff.Mode.MULTIPLY));
                    tunnel2.setLevel(11);

                    allCells[p1y][p1x].setForeground(tunnel1);
                    allCells[p2y][p2x].setForeground(tunnel2);


                    break;

                default:
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

                                if(allCells[i][x].getForeground()==ballDraw){
                                    Log.i("GameActivity","Hit by "+ allCells[i][x].getForeground()+"");
                                    Log.i("GameActivity","Match "+ ballDraw+"");

                                    if(lives==1){
                                        alive=false;
                                    }
                                    else{
                                        ballDraw.setColorFilter(new PorterDuffColorFilter(ballColor, PorterDuff.Mode.MULTIPLY));
                                        lives--;
                                    }

                                }
                            }

                        }
                        else{
                            int y=(edgeNum-14)/2;
                            y++;
                            for(int i=1;i<8;i++){
                                allCells[y][i].setBackgroundColor(laserColor);
                                if(allCells[y][i].getForeground()==ballDraw){ // CHECK NOT NULL
                                    Log.i("GameActivity","Hit by "+allCells[y][i].getForeground()+"");
                                    Log.i("GameActivity","Match "+ ballDraw+"");
                                    if(lives==1){
                                        alive=false;
                                    }
                                    else{
                                        ballDraw.setColorFilter(new PorterDuffColorFilter(ballColor, PorterDuff.Mode.MULTIPLY));
                                        lives--;
                                    }
                                }
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

                    break;

            }


    }};


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
    protected void onPause() {
        super.onPause();

        usedBack=true;
        alive=false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        if(ballColor==0){
            ballColor=Color.WHITE;
        }

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

        final TextView multiplier = (TextView) findViewById(R.id.multiplier);
        final TextView scoreView = (TextView) findViewById(R.id.score);

        multiplier.setMinimumHeight(screenHeight/24);
        multiplier.setMaxHeight(screenHeight/24);
        //multiplier.setTextSize(screenHeight/24);
        scoreView.setMinimumHeight(screenHeight/24);
        scoreView.setMaxHeight(screenHeight/24);
        //score.setTextSize(screenHeight/24);
        scoreView.setMinimumWidth(screenWidth/3+screenWidth/32);
        //score.setMaxWidth(screenWidth/3);

        int count=0;

        for(int i=0;i<12;i++){

            for(int j=0;j<9;j++){

                ImageView newPos = new ImageView(getApplicationContext());




                if(count%2 == 0) {
                    int color1 = Color.DKGRAY;
                    newPos.setBackgroundColor(color1);
                    allColors[i][j]=color1;
                } else if (count%2 == 1) {
                    int color2=Color.LTGRAY;
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
                allCells[i][j].setForeground(null);












            }}

        int indicator=0;
        for(int i=0;i<7;i++){
            edges[indicator]=allCells[0][i+1];
            allCells[0][i+1].setBackgroundColor(Color.BLACK);
            allColors[0][i+1]=Color.BLACK;

            indicator++;
            edges[indicator]=allCells[11][i+1];
            allCells[11][i+1].setBackgroundColor(Color.BLACK);
            allColors[11][i+1]=Color.BLACK;

            indicator++;
        }
        for(int i=0;i<10;i++){
            edges[indicator]=allCells[i+1][0];
            allCells[i+1][0].setBackgroundColor(Color.BLACK);
            allColors[i+1][0]=Color.BLACK;

            indicator++;
            edges[indicator]=allCells[i+1][8];
            allCells[i+1][8].setBackgroundColor(Color.BLACK);
            allColors[i+1][8]=Color.BLACK;

            indicator++;
        }

        allCells[0][0].setBackgroundColor(Color.BLACK);
        allColors[0][0]=Color.BLACK;

        allCells[11][8].setBackgroundColor(Color.BLACK);
        allColors[11][8]=Color.BLACK;

        allCells[0][8].setBackgroundColor(Color.BLACK);
        allColors[0][8]=Color.BLACK;

        allCells[11][0].setBackgroundColor(Color.BLACK);
        allColors[11][0]=Color.BLACK;

        //allCells[6][4].setBackgroundColor(Color.WHITE);
        ballDraw = (Drawable) getDrawable(R.drawable.ball);
        ballDraw.setColorFilter(new PorterDuffColorFilter(ballColor, PorterDuff.Mode.MULTIPLY));
        allCells[ball.y][ball.x].setForeground(ballDraw);
        //test

        new Thread(new Runnable() {
            //boolean dead=false;

            int wait=1500; // initial wait
            int blinks=10; // how many times the lasers blink
            int breakBetween=400; // break between spawns
            int numLasers=4;

            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                    while(isAlive()&&!usedBack) {


                        sendResetCommand();
                        int verticalLasers=0;
                        int horizontalLasers=0;

                        Random r= new Random();
                        ArrayList<Integer> fieldsUsed=new ArrayList();
                        int lasersSpawned=r.nextInt(numLasers)+3;
                        ArrayList<String> allLasers= new ArrayList<String>();



                        boolean powerupSpawn = r.nextInt(100)<40;

                        for(int i=0;i<lasersSpawned;i++){
                        int selectEdge = r.nextInt(34);

                        while(fieldsUsed.contains(selectEdge) || (selectEdge%2==0 && fieldsUsed.contains(selectEdge+1)) || (selectEdge%2==1&&fieldsUsed.contains(selectEdge-1))){
                            Log.i("GameActiviy","new select edge");
                            selectEdge = r.nextInt(34);
                        }
                            if(selectEdge<14){
                                if(verticalLasers<7){
                                    while(fieldsUsed.contains(selectEdge) || (selectEdge%2==0 && fieldsUsed.contains(selectEdge+1)) || (selectEdge%2==1&&fieldsUsed.contains(selectEdge-1))){
                                        Log.i("GameActiviy","new select edge");
                                        selectEdge = r.nextInt(14);
                                    }
                                    verticalLasers++;
                                }
                                else{
                                    selectEdge=r.nextInt(20)+14;
                                    while(fieldsUsed.contains(selectEdge) || (selectEdge%2==0 && fieldsUsed.contains(selectEdge+1)) || (selectEdge%2==1&&fieldsUsed.contains(selectEdge-1))){
                                        Log.i("GameActiviy","new select edge");
                                        selectEdge = r.nextInt(20)+14;
                                    }
                                }
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
                            sendCommand(i);
                            sendCommand("Shoot,"+i);
                        }
                        Thread.sleep(breakBetween);

                        if(powerupSpawn){
                            int powers=2; // change as we get more powerups

                            sendPowerCommand(r.nextInt(powers));
                        }

                        if(wait>200){
                            wait=wait-100;
                        }
                        if(breakBetween>100){
                            breakBetween=breakBetween-20;
                        }
                        if(breakBetween<=100 && blinks>7){
                            blinks--;
                        }
                        if(Math.round(getMultiplier())==getMultiplier() && lasersSpawned<7){
                            lasersSpawned++;
                        }

                    }
                    // END GAME

                    if(!usedBack) {
                        setRecentScore(score);

                        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);

                        // FIX NEXT ACTIVITY
                        startActivity(intent);
                    }


                }catch(InterruptedException e){

                }
            }
        }).start();
    }

    public void sendPowerCommand(int sel){
        Message msg = lasers.obtainMessage();
        Bundle bundle = new Bundle();

        bundle.putString("message",""+sel);

        msg.setData(bundle);
        lasers.sendMessage(msg);
    }

    public double getMultiplier(){
        return multiplier;
    }

    public void setRecentScore(int in){
        recentGameScore=in;
    }

    public boolean isAlive(){


        return alive;
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

    public void givePowers(int in){
        switch(in){
            case 10: // shield
                Log.i("GameActivity"," shield powerup");

                if(lives==1){
                    Log.i("GameActivity"," shield powerup inner");

                    ballDraw.setColorFilter(new PorterDuffColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY));

                    lives++;

                }
                break;
            case 11: // tunnel

                //Log.i("GameActivity"," tunnel powerup ");
                Point dest= new Point();

                int start=0;

                for(int i=0;i<2;i++){
                    if(tunnel[i].x==ball.x && tunnel[i].y==ball.y){
                        start=i;
                    }

                }

                if(start==1){
                    allCells[ball.y][ball.x].setForeground(null);

                    ball.x=tunnel[0].x;
                    ball.y=tunnel[0].y;

                    allCells[ball.y][ball.x].setForeground(ballDraw);


                }else{
                    allCells[ball.y][ball.x].setForeground(null);

                    ball.x=tunnel[1].x;
                    ball.y=tunnel[1].y;

                    allCells[ball.y][ball.x].setForeground(ballDraw);

                }

                break;
            default:
                Log.i("GameActivity"," default powerup");

                break;


        }
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float vel1, float vel2){
        //ImageView userBall= (ImageView) findViewById(R.id.userBall);
        //userBall.layout(2,3,1,2);
        if(alive){

        boolean moved=false;
        float deltaX =Math.abs(event1.getX()-event2.getX());
        float deltaY = Math.abs(event1.getY()-event2.getY());

        if(event1.getX()<event2.getX() && deltaX>100 && deltaX>deltaY){
            //move right
            if(ball.x!=7){
                //allCells[ball.y][ball.x].setBackgroundColor(Color.BLACK);
                allCells[ball.y][ball.x].setForeground(null);
                ball.x=ball.x+1;
                //allCells[ball.y][ball.x].setBackgroundColor(Color.WHITE);
                //Drawable ballDraw = (Drawable) getDrawable(R.drawable.ball);
                if(allCells[ball.y][ball.x].getForeground()!=null){
                    givePowers(allCells[ball.y][ball.x].getForeground().getLevel());
                }
                allCells[ball.y][ball.x].setForeground(ballDraw);
                moved=true;

            }
            //userBall.setBackgroundColor(Color.GREEN);

        } else if(event1.getX()>event2.getX() && deltaX>100 && deltaX>deltaY){
            // move left
            if(ball.x!=1){
                //allCells[ball.y][ball.x].setBackgroundColor(Color.BLACK);
                allCells[ball.y][ball.x].setForeground(null);
                ball.x=ball.x-1;
                //allCells[ball.y][ball.x].setBackgroundColor(Color.WHITE);
                //allCells[ball.y][ball.x].setBackgroundResource(R.drawable.ball);
                //Drawable ballDraw = (Drawable) getDrawable(R.drawable.ball);
                if(allCells[ball.y][ball.x].getForeground()!=null){
                    givePowers(allCells[ball.y][ball.x].getForeground().getLevel());
                }
                allCells[ball.y][ball.x].setForeground(ballDraw);
                moved=true;

            }

            //userBall.setBackgroundColor(Color.BLUE);

        }
        if(!moved){
            if(event1.getY()<event2.getY() && deltaY>100 && deltaY>=deltaX){
                //move down
                if(ball.y!=10){
                    //allCells[ball.y][ball.x].setBackgroundColor(Color.BLACK);
                    allCells[ball.y][ball.x].setForeground(null);
                    ball.y=ball.y+1;
                    //allCells[ball.y][ball.x].setBackgroundColor(Color.WHITE);
                   // Drawable ballDraw = (Drawable) getDrawable(R.drawable.ball);
                    if(allCells[ball.y][ball.x].getForeground()!=null){
                        givePowers(allCells[ball.y][ball.x].getForeground().getLevel());
                    }
                    allCells[ball.y][ball.x].setForeground(ballDraw);
                    moved=true;
                }
                // userBall.setBackgroundColor(Color.RED);

            } else if(event1.getY()>event2.getY() && deltaY>100 && deltaY>=deltaX) {
                //move up
                if (ball.y != 1) {
                    //allCells[ball.y][ball.x].setBackgroundColor(Color.BLACK);
                    allCells[ball.y][ball.x].setForeground(null);
                    ball.y = ball.y - 1;
                    //allCells[ball.y][ball.x].setBackgroundColor(Color.WHITE);
                    //Drawable ballDraw = (Drawable) getDrawable(R.drawable.ball);
                    if(allCells[ball.y][ball.x].getForeground()!=null){
                        givePowers(allCells[ball.y][ball.x].getForeground().getLevel());
                    }
                    allCells[ball.y][ball.x].setForeground(ballDraw);
                    moved = true;
                }
                // userBall.setBackgroundColor(Color.YELLOW);

            }
        }}


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






