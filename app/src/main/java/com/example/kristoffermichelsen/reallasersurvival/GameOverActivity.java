package com.example.kristoffermichelsen.reallasersurvival;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kristoffermichelsen.reallasersurvival.Database.Score;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class GameOverActivity extends AppCompatActivity {

    private Realm realm;
    private int newHighscore;
    private RealmResults<Score> results;
    TextView no1, no2, no3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        //Get the database ready for action
        realm = Realm.getDefaultInstance();

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

        newHighscore = GameActivity.recentGameScore;

        no1 = (TextView) findViewById(R.id.no1);
        no2 = (TextView) findViewById(R.id.no2);
        no3 = (TextView) findViewById(R.id.no3);

        showTop3();

    }

    private void saveHighscore() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Score score = new Score();
                score.setScore(newHighscore);
                realm.copyToRealm(score);
            }
        });
    }

    private void showTop3() {
        results = realm.where(Score.class).findAll();
        results.sort("score", Sort.DESCENDING);
        ArrayList<Integer> tempScore = new ArrayList<Integer>();

        for (int i=0;i<3;i++){
            if(results.get(i)!=null){
                tempScore.add(results.get(i).getScore());
            }else{
                tempScore.add(0);
            }
        }
        no1.setText("#1 "+tempScore.get(0));
        no2.setText("#2 "+tempScore.get(1));
        no3.setText("#3 "+tempScore.get(2));

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveHighscore();
    }
}



