package com.example.kristoffermichelsen.reallasersurvival;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.content.*;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    MediaPlayer mpthree;
    static int savedColor;

    static Settings settings = Settings.getInstance();

    //Used for grid toggle switch
    int[][] onState = new int[][]{
            new int[]{-android.R.attr.state_enabled}, // enabled
    };
    int[] onColor = new int[]{
            Color.GREEN,
    };
    int[][] offState = new int[][]{
            new int[]{-android.R.attr.state_enabled}, // enabled
    };
    int[] offColor = new int[]{
            Color.RED,
    };
    ColorStateList on = new ColorStateList(onState, onColor);
    ColorStateList off = new ColorStateList(offState, offColor);
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView title = (TextView) findViewById(R.id.settings);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ARDESTINE.ttf");
        title.setTypeface(font);
        title.setTextSize(45);
        load();


        Button mainMenu = (Button) findViewById(R.id.mainMenuButton);

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenu = new Intent(SettingsActivity.this, MainMenuActivity.class);
                startActivity(mainMenu);
            }
        });

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupBallColor);
        final RadioButton white = (RadioButton) findViewById(R.id.white);
        final RadioButton blue = (RadioButton) findViewById(R.id.blue);
        final RadioButton green = (RadioButton) findViewById(R.id.green);
        final RadioButton yellow = (RadioButton) findViewById(R.id.yellow);

        int rG1_CheckId = radioGroup.getCheckedRadioButtonId();
        RadioButton [] radioButtons = {white,blue,green,yellow};


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);

                boolean isChecked = radioButton.isChecked();

                if (isChecked) {
                    GameActivity.ballColor = radioButton.getCurrentTextColor();
                    savedColor = radioButton.getCurrentTextColor();
                    save(radioButton.getId());

                }else{
                    GameActivity.ballColor = Color.WHITE;
                    savedColor=Color.WHITE;
                }
            }
        });

        if(!blue.isChecked() && !green.isChecked() && !yellow.isChecked()){
            savedColor=Color.WHITE;
            white.setChecked(true);
        }
        for(int i=0; i<radioButtons.length;i++){
            if(radioButtons[i].isChecked()){
                savedColor=radioButtons[i].getCurrentTextColor();
            }
        }

        final Switch toggleGrid = (Switch) findViewById(R.id.showGridSwitch);
        toggleGrid.setChecked(settings.useGrid);

        if (toggleGrid.isChecked()) {
            toggleGrid.setTrackTintList(on);
            settings.useGrid = true;
        } else {
            toggleGrid.setTrackTintList(off);
            settings.useGrid = false;
        }

        toggleGrid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
;
                if (toggleGrid.isChecked()) {
                    toggleGrid.setTrackTintList(on);
                    settings.useGrid = true;
                } else {
                    toggleGrid.setTrackTintList(off);
                    settings.useGrid = false;
                }
            }
        });


    }

    private void save(int radioid) {

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("check", radioid);
        editor.commit();
    }

    private void load() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        int radioId = sharedPreferences.getInt("check", 0);
        if (radioId > 0) {
            RadioButton rbtn = (RadioButton) findViewById(radioId);
            rbtn.setChecked(true);
            //savedColor = rbtn.getCurrentTextColor();
            GameActivity.ballColor=rbtn.getCurrentTextColor();
        }

    }



    @Override
    protected void onPause() {
        super.onPause();
        mpthree.stop();
        mpthree.release();


    }


    @Override
    protected void onResume() {
        super.onResume();
        mpthree = MediaPlayer.create(SettingsActivity.this, R.raw.two);
        if (!mpthree.isPlaying()) {
            mpthree.start();
            mpthree.setLooping(true);
        }
    }

}
