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
import io.realm.RealmResults;
import io.realm.Sort;

public class HighscoreActivity extends AppCompatActivity {

   // private Realm realm;
    //private RealmResults<Score> results;
   // TextView no1, no2, no3, no4, no5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        //Get the database ready for action
        //realm = Realm.getDefaultInstance();





        TextView title = (TextView) findViewById(R.id.highscores);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/ARDESTINE.ttf");
        title.setTypeface(font);
        title.setTextSize(45);

       // no1 = (TextView) findViewById(R.id.no1);
       // no2 = (TextView) findViewById(R.id.no2);
       // no3 = (TextView) findViewById(R.id.no3);
       // no4 = (TextView) findViewById(R.id.no4);
       // no5 = (TextView) findViewById(R.id.no5);

       // showTop5();

        Button mainMenu = (Button) findViewById(R.id.mainMenuButton);

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(HighscoreActivity.this, MainMenuActivity.class);
                startActivity(mainMenu);
            }
        });

    }
/*
    private void showTop5() {
        results = realm.where(Score.class).findAll();
        results.sort("score", Sort.DESCENDING);
        ArrayList<Integer> tempScore = new ArrayList<Integer>();

        for (int i=0;i<5;i++){
            if(results.get(i)!=null){
                tempScore.add(results.get(i).getScore());
            }else{
                tempScore.add(0);
            }
        }
        no1.setText("#1 "+tempScore.get(0));
        no2.setText("#2 "+tempScore.get(1));
        no3.setText("#3 "+tempScore.get(2));
        no1.setText("#4 "+tempScore.get(3));
        no2.setText("#5 "+tempScore.get(4));
    }

*/




}
