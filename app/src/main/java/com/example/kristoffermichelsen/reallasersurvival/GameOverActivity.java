package com.example.kristoffermichelsen.reallasersurvival;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.kristoffermichelsen.reallasersurvival.Database.Score;

import java.lang.reflect.Array;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class GameOverActivity extends AppCompatActivity {

    private Realm realm;
    TextView no1, no2, no3, preview;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        mp = MediaPlayer.create(GameOverActivity.this, R.raw.gameover);
        mp.start();
        //Get the database ready for action
        realm = Realm.getDefaultInstance();

        TextView title = (TextView) findViewById(R.id.gameOver);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ARDESTINE.ttf");
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

        preview = (TextView) findViewById(R.id.yourScore);
        no1 = (TextView) findViewById(R.id.no1);
        no2 = (TextView) findViewById(R.id.no2);
        no3 = (TextView) findViewById(R.id.no3);
        preview.setText("Your Score: "+GameActivity.recentGameScore);
        saveHighscore();
        showTop3();



    }

    private void saveHighscore() {
        realm.beginTransaction();
        Score score = realm.createObject(Score.class);
        score.setScore(GameActivity.recentGameScore);
        realm.commitTransaction();
    }

    private void showTop3() {
        RealmQuery<Score> query = realm.where(Score.class);
        RealmResults<Score> result = query.findAll();
        result = result.sort("score", Sort.DESCENDING);

        if (result.size() == 1) {
            no1.setText("#1 " + result.get(0).getScore());
            no2.setText("#2 0");
            no3.setText("#3 0");
        } else if (result.size() == 2) {
            no1.setText("#1 " + result.get(0).getScore());
            no2.setText("#2 " + result.get(1).getScore());
            no3.setText("#3 0");
        } else {
            no1.setText("#1 " + result.get(0).getScore());
            no2.setText("#2 " + result.get(1).getScore());
            no3.setText("#3 " + result.get(2).getScore());

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mp.stop();
        mp.release();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(GameOverActivity.this,MainMenuActivity.class);
        startActivity(intent);
    }
}



