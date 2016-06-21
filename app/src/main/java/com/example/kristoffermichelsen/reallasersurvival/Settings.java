package com.example.kristoffermichelsen.reallasersurvival;

import android.graphics.Color;

public class Settings {

    boolean useGrid = false;
    int ballColor = Color.WHITE;
    private static Settings instance = null;
    private Settings(){};
    public static Settings getInstance() {
        if (instance == null)
            instance = new Settings();
        return instance;
    }

}
