package com.example.kristoffermichelsen.reallasersurvival;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.audiofx.BassBoost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView title = (TextView) findViewById(R.id.settings);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/ARDESTINE.ttf");
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

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroupBallColor);
        RadioButton white = (RadioButton) findViewById(R.id.white);
        RadioButton blue = (RadioButton) findViewById(R.id.blue);
        RadioButton green = (RadioButton) findViewById(R.id.green);
        RadioButton yellow = (RadioButton) findViewById(R.id.yellow);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton radioButton = (RadioButton)group.findViewById(checkedId);

                boolean isChecked = radioButton.isChecked();

                if (isChecked)
                {
                    //skift farve på bold til farven på radioButton
                }
            }
        });


    }
}
