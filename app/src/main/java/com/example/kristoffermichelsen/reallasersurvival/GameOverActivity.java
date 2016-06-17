package com.example.kristoffermichelsen.reallasersurvival;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileWriter;
import java.util.Collections;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView title = (TextView) findViewById(R.id.gameOver);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/ARDESTINE.ttf");
        title.setTypeface(font);
        title.setTextSize(45);

        Button playAgain = (Button) findViewById(R.id.playAgainButton);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playAgain = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity(playAgain);
            }
        });

        Button mainMenu = (Button) findViewById(R.id.mainMenuButton);

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(GameOverActivity.this, MainMenuActivity.class);
                startActivity(mainMenu);
            }
        });
        //TODO - Change this to Recent Score from Game Activity

        int newHighscore = GameActivity.recentGameScore;

            MainMenuActivity.highscores.add(newHighscore);
            Collections.sort(MainMenuActivity.highscores);
            MainMenuActivity.highscores.remove(0);
            Collections.reverse(MainMenuActivity.highscores);

        TextView no1 = (TextView) findViewById(R.id.no1);
        TextView no2 = (TextView) findViewById(R.id.no2);
        TextView no3 = (TextView) findViewById(R.id.no3);

        no1.setText("#1 "+MainMenuActivity.highscores.get(0));
        no2.setText("#2 "+MainMenuActivity.highscores.get(1));
        no3.setText("#3 "+MainMenuActivity.highscores.get(2));

    }
    public void saveHighscore() {
        try{
            FileWriter highscorePrinter = new FileWriter("Highscore.txt");
            for(int i = 0;i <= 4;i++){
                String j = MainMenuActivity.highscores.get(i).toString();
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



