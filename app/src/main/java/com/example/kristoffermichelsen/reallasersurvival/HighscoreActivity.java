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

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class HighscoreActivity extends AppCompatActivity {

   private Realm realm;
   TextView no1, no2, no3, no4, no5;
    MediaPlayer mptwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        realm = Realm.getDefaultInstance();


        TextView title = (TextView) findViewById(R.id.highscores);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/ARDESTINE.ttf");
        title.setTypeface(font);
        title.setTextSize(45);

        no1 = (TextView) findViewById(R.id.no1);
        no2 = (TextView) findViewById(R.id.no2);
        no3 = (TextView) findViewById(R.id.no3);
        no4 = (TextView) findViewById(R.id.no4);
        no5 = (TextView) findViewById(R.id.no5);
        showTop5();

        Button mainMenu = (Button) findViewById(R.id.mainMenuButton);

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(HighscoreActivity.this, MainMenuActivity.class);
                startActivity(mainMenu);
            }
        });
    }

    private void showTop5() {
            RealmQuery<Score> query = realm.where(Score.class);
            RealmResults<Score> result = query.findAll();
            result = result.sort("score", Sort.DESCENDING);

        if(result.size()==0) {
            no1.setText("#1 0");
            no2.setText("#2 0");
            no3.setText("#3 0");
            no4.setText("#4 0");
            no5.setText("#5 0");
        } else if (result.size() == 1) {
            no1.setText("#1 " + result.get(0).getScore());
            no2.setText("#2 0");
            no3.setText("#3 0");
            no4.setText("#4 0");
            no5.setText("#5 0");
        } else if (result.size() == 2) {
            no1.setText("#1 " + result.get(0).getScore());
            no2.setText("#2 " + result.get(1).getScore());
            no3.setText("#3 0");
            no4.setText("#4 0");
            no5.setText("#5 0");
        } else if (result.size() == 3) {

            no1.setText("#1 " + result.get(0).getScore());
            no2.setText("#2 " + result.get(1).getScore());
            no3.setText("#3 " + result.get(2).getScore());
            no4.setText("#4 0");
            no5.setText("#5 0");

        } else if (result.size()==4) {
            no1.setText("#1 " + result.get(0).getScore());
            no2.setText("#2 " + result.get(1).getScore());
            no3.setText("#3 " + result.get(2).getScore());
            no4.setText("#4 " + result.get(3).getScore());
            no5.setText("#5 0");
        } else {
            no1.setText("#1 " + result.get(0).getScore());
            no2.setText("#2 " + result.get(1).getScore());
            no3.setText("#3 " + result.get(2).getScore());
            no4.setText("#4 " + result.get(3).getScore());
            no5.setText("#5 " + result.get(4).getScore());

        }

        }

    @Override
    protected void onPause() {
        super.onPause();
        mptwo.stop();
        mptwo.release();


    }

    @Override
    protected void onResume() {
        super.onResume();
        mptwo = MediaPlayer.create(HighscoreActivity.this, R.raw.mainmenu);
        if (!mptwo.isPlaying()) {
            mptwo.start();
            mptwo.setLooping(true);

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(HighscoreActivity.this,MainMenuActivity.class);
        startActivity(intent);
    }

}
