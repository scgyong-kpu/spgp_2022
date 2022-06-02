package net.scgyong.and.taptu.game;

import android.util.Log;
import android.view.MotionEvent;

import net.scgyong.and.taptu.R;

import java.util.ArrayList;

import kr.ac.kpu.game.framework.game.Scene;
import kr.ac.kpu.game.framework.interfaces.GameObject;
import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.res.Metrics;

public class MainScene extends Scene implements Pret.Listener {
    public static final String PARAM_SONG_FILENAME = "song_filename";
    private static final String TAG = MainScene.class.getSimpleName();
    private static MainScene singleton;
    private Song song;
    private Pret[] prets = new Pret[5];
    private NoteGen noteGenerator;

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
        noteGenerator = new NoteGen(song);
        add(Layer.controller.ordinal(), noteGenerator);

        for (int lane = 0; lane < 5; lane++) {
            Pret pret = new Pret(lane, this);
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

    @Override
    public void onPret(int lane, boolean pressed) {
        Log.d(TAG, "onPret: lane=" + lane + " pressed=" + pressed);
        if (!pressed) return;
        NoteSprite ns = findNearestNote(lane);
        if (ns == null) return;
        remove(ns);
    }

    private NoteSprite findNearestNote(int lane) {
        float dist = Float.MAX_VALUE;
        float time = noteGenerator.getTime();
        NoteSprite nearest = null;
        ArrayList<GameObject> notes = objectsAt(Layer.note.ordinal());
        for (GameObject go : notes) {
            if (!(go instanceof NoteSprite)) continue;
            NoteSprite ns = (NoteSprite) go;
            if (ns.note.lane != lane) continue;
            float diff = ns.note.msec / 1000.0f - time;
            if (diff < 0) diff = -diff;
            if (dist > diff) {
                Log.d(TAG, "= dist=" + dist + " diff=" + diff);
                dist = diff;
                nearest = ns;
            }
        }
        Log.d(TAG, "dist=" + dist + " nearest=" + nearest.note.msec);
        return (dist < 1.0f) ? nearest : null;
    }
}
