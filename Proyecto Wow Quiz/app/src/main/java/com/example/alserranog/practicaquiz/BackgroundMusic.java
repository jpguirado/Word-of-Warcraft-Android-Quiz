package com.example.alserranog.practicaquiz;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;



public class BackgroundMusic extends Service {

    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        player = MediaPlayer.create(this,R.raw.backgroundmusic);

        player.setLooping(true);
        player.start();


        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        player.stop();
    }
}