package com.example.kristoffermichelsen.reallasersurvival;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainMenuActivity extends AppCompatActivity {

    private Realm realm;
    private RealmConfiguration realmConfig;
    MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        realmConfig = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getInstance(realmConfig);

        TextView title = (TextView) findViewById(R.id.title);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ARDESTINE.ttf");
        title.setTypeface(font);
        title.setTextSize(45);



        final Button startButton = (Button) findViewById(R.id.startButton);

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
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();
        mp.release();
        }






    @Override
    protected void onResume() {
        super.onResume();
        mp = MediaPlayer.create(MainMenuActivity.this, R.raw.crash);
        mp.start();
        mp = MediaPlayer.create(MainMenuActivity.this, R.raw.mainmenuloop);
        mp.start();
        mp.setLooping(true);
        }




    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}






