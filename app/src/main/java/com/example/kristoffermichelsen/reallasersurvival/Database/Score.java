package com.example.kristoffermichelsen.reallasersurvival.Database;

import io.realm.RealmObject;

/**
 * Created by Christian on 18-06-2016.
 */
public class Score extends RealmObject {

    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
