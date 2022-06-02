package net.scgyong.and.taptu.game;

import kr.ac.kpu.game.framework.game.Scene;

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
        note, controller, COUNT;
    }

    public void init() {
        super.init();

        initLayers(Layer.COUNT.ordinal());

        add(Layer.controller.ordinal(), new NoteGen(song));
    }
}
