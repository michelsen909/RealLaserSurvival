package com.example.kristoffermichelsen.reallasersurvival;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.media.audiofx.BassBoost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.content.*;

public class SettingsActivity extends AppCompatActivity {
    static int savedColor;

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
        //RadioButton white = (RadioButton) findViewById(R.id.white);
        //RadioButton blue = (RadioButton) findViewById(R.id.blue);
        //RadioButton green = (RadioButton) findViewById(R.id.green);
        //RadioButton yellow = (RadioButton) findViewById(R.id.yellow);

        int rG1_CheckId = radioGroup.getCheckedRadioButtonId();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);

                boolean isChecked = radioButton.isChecked();

                if (isChecked) {
                    GameActivity.ballColor = radioButton.getCurrentTextColor();
                    savedColor = radioButton.getCurrentTextColor();
                    save(radioButton.getId());

                }
            }
        });

        final Switch toggleGrid = (Switch) findViewById(R.id.showGridSwitch);

        toggleGrid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int[][] green = new int[][]{
                        new int[]{-android.R.attr.state_enabled}, // enabled
                };

                int[] colors = new int[]{
                        Color.GREEN,
                };

                int[][] red = new int[][]{
                        new int[]{-android.R.attr.state_enabled}, // enabled
                };

                int[] color = new int[]{
                        Color.RED,
                };


                ColorStateList on = new ColorStateList(green, colors);
                ColorStateList off = new ColorStateList(red, color);
                if (toggleGrid.isChecked()) {
                    toggleGrid.setTrackTintList(on);
                } else {
                    toggleGrid.setTrackTintList(off);
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
        }

    }

}
