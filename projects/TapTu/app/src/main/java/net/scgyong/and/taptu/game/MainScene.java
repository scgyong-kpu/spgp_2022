package net.scgyong.and.taptu.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import net.scgyong.and.taptu.R;

import java.util.ArrayList;

import kr.ac.kpu.game.framework.game.Scene;
import kr.ac.kpu.game.framework.interfaces.GameObject;
import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.res.BitmapPool;
import kr.ac.kpu.game.framework.res.Metrics;

public class MainScene extends Scene implements Pret.Listener {
    public static final String PARAM_SONG_FILENAME = "song_filename";
    private static final String TAG = MainScene.class.getSimpleName();
    private static MainScene singleton;
    private Song song;
    private Pret[] prets = new Pret[5];
    Call call;
    private NoteGen noteGenerator;

    public static MainScene get() {
        if (singleton == null) {
            singleton = new MainScene();
        }
        return singleton;
    }

    public boolean loadSong(String fileName) {
        //song = new Song(fileName);
        //return song.isValid();
        return false;
    }

    public enum Layer {
        bg, pret, note, call, controller, COUNT;
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

        call = new Call();
        add(Layer.call.ordinal(), call);
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

    RectF tmpRect = new RectF();
    @Override
    public void draw(Canvas canvas) {
        float time = noteGenerator.getTime();
        super.draw(canvas);
        Bitmap bitmap = BitmapPool.get(R.mipmap.trans_50p);
        for (int lane = 0; lane < 5; lane++) {
            float diff = 1.0f;
            NoteSprite ns = findNearestNote(lane, time);
            if (ns != null) {
                diff = time - ns.note.msec / 1000.0f;
                if (diff < 0) diff = -diff;
            }
            float left = NoteSprite.NOTE_WIDTH * Metrics.width;
            float width = NoteSprite.NOTE_WIDTH * Metrics.width;
            left += width * lane;
            float top = (1 - NoteSprite.NOTE_Y_HIT_MARGIN) * Metrics.height;
            tmpRect.set(left, top, left + width, top + diff * 100);
            canvas.drawBitmap(bitmap, null, tmpRect, null);
        }
    }

    @Override
    public void onPret(int lane, boolean pressed) {
        Log.d(TAG, "onPret: lane=" + lane + " pressed=" + pressed);
        if (!pressed) return;
        float time = noteGenerator.getTime();
        NoteSprite ns = findNearestNote(lane, time);
        if (ns == null) return;
        float diff = ns.note.msec / 1000.0f - time;
        if (diff < 0) diff = -diff;
        Call.Type type = Call.Type.miss;
        if (diff < 0.1) {
            type = Call.Type.perfect;
        } else if (diff < 0.2) {
            type = Call.Type.great;
        } else if (diff < 0.3) {
            type = Call.Type.good;
        } else if (diff < 0.5) {
            type = Call.Type.bad;
        }
        call.set(type);
        remove(ns);
    }

    private NoteSprite findNearestNote(int lane, float time) {
        float dist = Float.MAX_VALUE;
        NoteSprite nearest = null;
        ArrayList<GameObject> notes = objectsAt(Layer.note.ordinal());
        for (GameObject go : notes) {
            if (!(go instanceof NoteSprite)) continue;
            NoteSprite ns = (NoteSprite) go;
            if (ns.note.lane != lane) continue;
            float diff = ns.note.msec / 1000.0f - time;
            if (diff < 0) diff = -diff;
            if (dist > diff) {
//                Log.d(TAG, "= dist=" + dist + " diff=" + diff);
                dist = diff;
                nearest = ns;
            }
        }
//        if (nearest != null) {
//            Log.d(TAG, "dist=" + dist + " nearest=" + nearest.note.msec);
//        }
        return (dist < 1.0f) ? nearest : null;
    }
}
