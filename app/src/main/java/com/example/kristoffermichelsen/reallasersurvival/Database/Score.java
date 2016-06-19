package com.example.kristoffermichelsen.reallasersurvival.Database;

import io.realm.RealmObject;


public class Score extends RealmObject {

    private int score;

    public int getScore() {

        return score;
    }

    public void setScore(int score) {

        this.score = score;
    }

}
