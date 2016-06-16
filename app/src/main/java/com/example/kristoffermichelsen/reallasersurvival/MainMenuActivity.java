package com.example.kristoffermichelsen.reallasersurvival;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainMenuActivity extends AppCompatActivity {

    static ArrayList<Integer> highscores = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        TextView title = (TextView) findViewById(R.id.title);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/ARDESTINE.ttf");
        title.setTypeface(font);
        title.setTextSize(45);
        createHighscore();

        Button startButton = (Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(MainMenuActivity.this, GameActivity.class);
                startActivity(start);
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

        Button testButton = (Button) findViewById(R.id.testButton);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test = new Intent(MainMenuActivity.this, GameActivity2.class);
                startActivity(test);
            }
        });
    }
    public static void createHighscore() {
        try {
            Scanner highscoreFile = new Scanner(new File("scores/Highscore.txt"));
            highscores = new ArrayList<Integer>();
            for (int i = 0; i <= 4; i++) {
                highscores.add(highscoreFile.nextInt());
            }
            highscoreFile.close();

        } catch (FileNotFoundException e){

        }
    }
    public void saveHighscore() {
        try{
            FileWriter highscorePrinter = new FileWriter("Highscore.txt");
            for(int i = 0;i <= 4;i++){
                String j = highscores.get(i).toString();
                highscorePrinter.write(j + " ");
            }
            highscorePrinter.close();
        } catch (java.io.IOException e){

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveHighscore();
    }
}

