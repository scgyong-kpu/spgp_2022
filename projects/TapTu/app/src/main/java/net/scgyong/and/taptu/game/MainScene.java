package net.scgyong.and.taptu.game;

import net.scgyong.and.taptu.R;

import kr.ac.kpu.game.framework.game.Scene;
import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.res.Metrics;

public class MainScene extends Scene {
    public static final String PARAM_SONG_FILENAME = "song_filename";
    private static MainScene singleton;
    private Song song;

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
        bg, note, controller, COUNT;
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
    }
}
