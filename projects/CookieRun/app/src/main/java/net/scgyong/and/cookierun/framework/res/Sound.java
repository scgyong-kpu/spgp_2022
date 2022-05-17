package net.scgyong.and.cookierun.framework.res;

import android.media.MediaPlayer;

import net.scgyong.and.cookierun.framework.view.GameView;

public class Sound {
    protected static MediaPlayer mediaPlayer;
    public static void playMusic(int resId) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(GameView.view.getContext(), resId);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }
    public static void stopMusic() {
        if (mediaPlayer == null) return;
        mediaPlayer.stop();
        mediaPlayer = null;
    }
    public static void pauseMusic() {
        if (mediaPlayer == null) return;
        mediaPlayer.pause();
    }
    public static void resumeMusic() {
        if (mediaPlayer == null) return;
        mediaPlayer.start();
    }
}
