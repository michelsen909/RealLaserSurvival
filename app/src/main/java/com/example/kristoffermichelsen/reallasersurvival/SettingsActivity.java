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

        if (settings.ballColor == Color.WHITE) {
            white.setChecked(true);
        } else if (settings.ballColor == Color.rgb(101, 140, 255)) {
            blue.setChecked(true);
        } else if (settings.ballColor == Color.rgb(0, 255, 120)) {
            green.setChecked(true);
        } else if (settings.ballColor == Color.rgb(255, 221, 7)) {
            yellow.setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.white) {
                    settings.ballColor = Color.WHITE;
                    white.setChecked(true);
                } else if (checkedId == R.id.blue) {
                    settings.ballColor = Color.rgb(101, 140, 255);
                    blue.setChecked(true);
                } else if (checkedId == R.id.green) {
                    settings.ballColor = Color.rgb(0, 255, 120);
                    green.setChecked(true);
                } else if (checkedId == R.id.yellow) {
                    settings.ballColor = Color.rgb(255, 221, 7);
                    yellow.setChecked(true);
                } else {
                    white.setChecked(true);
                    settings.ballColor = Color.WHITE;
                }
            }
        });

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

    @Override
    protected void onPause() {
        super.onPause();
        mpthree.stop();
        mpthree.release();


    }


    @Override
    protected void onResume() {
        super.onResume();
        mpthree = MediaPlayer.create(SettingsActivity.this, R.raw.crash);
            mpthree.start();
        if(!mpthree.isPlaying()) {
            mpthree.stop();
            mpthree.release();
            mpthree = MediaPlayer.create(SettingsActivity.this, R.raw.mainmenuloop);
            mpthree.start();
            mpthree.setLooping(true);
        }


            }


        @Override
        public void onBackPressed () {
            super.onBackPressed();

            Intent intent = new Intent(SettingsActivity.this, MainMenuActivity.class);
            startActivity(intent);
        }

}