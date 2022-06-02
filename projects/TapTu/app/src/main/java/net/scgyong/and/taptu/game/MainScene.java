package net.scgyong.and.taptu.game;

import android.view.MotionEvent;

import net.scgyong.and.taptu.R;

import kr.ac.kpu.game.framework.game.Scene;
import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.res.Metrics;

public class MainScene extends Scene {
    public static final String PARAM_SONG_FILENAME = "song_filename";
    private static MainScene singleton;
    private Song song;
    private Pret[] prets = new Pret[5];

    public static MainScene get() {
        if (singleton == null) {
            singleton = new MainScene();
        }
        return singleton;
    }

    public boolean loadSong(String fileName) {
        song = new Song(fileName);
        return song.isValid();
    }

    public enum Layer {
        bg, pret, note, controller, COUNT;
    }

    public void init() {
        super.init();

        initLayers(Layer.COUNT.ordinal());

        add(Layer.bg.ordinal(), new Sprite(
                Metrics.width / 2, Metrics.height / 2,
                Metrics.width, Metrics.height,
                R.mipmap.bg
        ));
        add(Layer.controller.ordinal(), new NoteGen(song));

        for (int lane = 0; lane < 5; lane++) {
            Pret pret = new Pret(lane);
            prets[lane] = pret;
            add(Layer.pret.ordinal(), pret);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for (int lane = 0; lane < 5; lane++) {
            boolean processed = prets[lane].onTouchEvent(event);
            if (processed) {
                return true;
            }
        }
        return false;
    }
}
