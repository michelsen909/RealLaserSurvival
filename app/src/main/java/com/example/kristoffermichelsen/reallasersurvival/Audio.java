package com.example.kristoffermichelsen.reallasersurvival;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;


public class Audio extends Service{
    MediaPlayer audio;
    public void onCreate(){
        super.onCreate();
        audio = MediaPlayer.create(this,R.raw.elevator);
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        audio.start();
        audio.setLooping(true);
        return 1;
    }

    public void onStop(){
        audio.stop();
        audio.release();

    }

    public void onPause(){
        audio.stop();
        audio.release();
    }
    public void onDestroy(){
        audio.stop();
        audio.release();
    }



    //Default made, dont use
    @Override
    public IBinder onBind(Intent objIndent) {
        return null;
    }
}