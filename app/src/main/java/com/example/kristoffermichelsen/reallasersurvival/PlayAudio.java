package com.example.kristoffermichelsen.reallasersurvival;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;



public class PlayAudio extends Service{
    MediaPlayer audio;
    float volume = 1;
    float speed = 0.05f;

    public void onCreate(){
        super.onCreate();
        audio = MediaPlayer.create(this,R.raw.elevator);
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        audio.start();
        if(audio.isLooping() != true){
        }
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

    public void FadeOut(float deltaTime)
    {
        audio.setVolume(volume, volume);
        volume -= speed* deltaTime;

    }
    //Default made, dont use
    @Override
    public IBinder onBind(Intent objIndent) {
        return null;
    }
}