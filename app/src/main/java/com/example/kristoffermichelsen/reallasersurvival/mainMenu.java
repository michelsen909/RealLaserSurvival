package com.example.kristoffermichelsen.reallasersurvival;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.audiofx.BassBoost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class mainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        TextView title = (TextView) findViewById(R.id.textView);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/ARDESTINE.ttf");
        title.setTypeface(font);
        title.setTextSize(45);


        Button startGame = (Button) findViewById(R.id.button);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(mainMenu.this, SettingsActivity.class);
                startActivity(start);
            }
        });

        Button settings = (Button) findViewById(R.id.settingsButton);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(mainMenu.this, SettingsActivity.class);
                startActivity(start);
            }
        });

        Button highscore = (Button) findViewById(R.id.highscore);

        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(mainMenu.this, SettingsActivity.class);
                startActivity(start);
            }
        });

     }
}
