package com.spacekuukan.application.function;

import android.content.Context;
import android.media.MediaPlayer;

import com.spacekuukan.application.R;

public class BackgroundMusicThread extends Thread {

    Context context;
    MediaPlayer main_theme;

    public BackgroundMusicThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        super.run();
        main_theme = MediaPlayer.create(context, R.raw.jaunter_thesearch);
        main_theme.setLooping(true);
        main_theme.setVolume(100, 100);
        main_theme.start();
    }

    public void stopMainTheme() {
        main_theme.stop();
    }

}
