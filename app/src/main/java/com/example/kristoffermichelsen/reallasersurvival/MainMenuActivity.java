package com.example.kristoffermichelsen.reallasersurvival;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenuActivity extends AppCompatActivity {

    static ArrayList<Integer> highscores = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        TextView title = (TextView) findViewById(R.id.title);
        playAudio(title);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/ARDESTINE.ttf");
        title.setTypeface(font);
        title.setTextSize(45);
        createHighscore();

        final Button startButton = (Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(MainMenuActivity.this, GameActivity.class);
                startActivity(start);
                stopAudio(startButton);

            }
        });



        Button settingsButton = (Button) findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(MainMenuActivity.this, SettingsActivity.class);
                startActivity(settings);
            }
        });

        Button highscoreButton = (Button) findViewById(R.id.highscoreButton);

        highscoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent highscore = new Intent(MainMenuActivity.this, HighscoreActivity.class);
                startActivity(highscore);
            }
        });


        // TEST!

        Button testStartButton = (Button) findViewById(R.id.testStartButton);

        testStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testStart = new Intent(MainMenuActivity.this, GameActivity2.class);
                startActivity(testStart);
            }
        });

        Button testGameOverButton = (Button) findViewById(R.id.testGameOverButton);

        testGameOverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testGameOver = new Intent(MainMenuActivity.this, GameOverActivity.class);
                startActivity(testGameOver);
            }
        });

    }


    public static void createHighscore() {
        try {
            Scanner highscoreFile = new Scanner(new File("scores/Highscore.txt"));

            for (int i = 0; i < 5; i++) {
                highscores.add(Integer.parseInt(highscoreFile.next()));
                Log.i("Highscore",highscores.toString() );
            }
            highscoreFile.close();

        } catch (FileNotFoundException e){
            Log.i("Highscore","File not found" );

        }

    }
    public void playAudio(View view) {
        Intent objIntent = new Intent(this, PlayAudio.class);
        startService(objIntent);
    }

    public void stopAudio(View view) {
        Intent objIntent = new Intent(this, PlayAudio.class);
        stopService(objIntent);
    }


    @Override
    protected void onResume(){
        super.onResume();
        playAudio(findViewById(R.id.title));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAudio(findViewById(R.id.title));
    }
}



