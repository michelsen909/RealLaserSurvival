package com.example.kristoffermichelsen.reallasersurvival;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HighscoreActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);



        TextView title = (TextView) findViewById(R.id.highscores);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/ARDESTINE.ttf");
        title.setTypeface(font);
        title.setTextSize(45);

        TextView no1 = (TextView) findViewById(R.id.no1);
        TextView no2 = (TextView) findViewById(R.id.no2);
        TextView no3 = (TextView) findViewById(R.id.no3);
        TextView no4 = (TextView) findViewById(R.id.no4);
        TextView no5 = (TextView) findViewById(R.id.no5);

        no1.setText("#1 "+MainMenuActivity.highscores.get(0));
        no2.setText("#2 "+MainMenuActivity.highscores.get(1));
        no3.setText("#3 "+MainMenuActivity.highscores.get(2));
        no4.setText("#4 "+MainMenuActivity.highscores.get(3));
        no5.setText("#5 "+MainMenuActivity.highscores.get(4));

        Button mainMenu = (Button) findViewById(R.id.mainMenuButton);

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(HighscoreActivity.this, MainMenuActivity.class);
                startActivity(mainMenu);
            }
        });




    }








}
