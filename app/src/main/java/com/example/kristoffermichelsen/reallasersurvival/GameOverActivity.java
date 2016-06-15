package com.example.kristoffermichelsen.reallasersurvival;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView title = (TextView) findViewById(R.id.title);
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




    }
}
