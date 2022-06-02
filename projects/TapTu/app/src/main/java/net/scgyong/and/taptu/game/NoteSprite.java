package net.scgyong.and.taptu.game;

import android.util.Log;

import net.scgyong.and.taptu.R;

import kr.ac.kpu.game.framework.game.RecycleBin;
import kr.ac.kpu.game.framework.interfaces.Recyclable;
import kr.ac.kpu.game.framework.objects.Sprite;
import kr.ac.kpu.game.framework.res.Metrics;

public class NoteSprite extends Sprite implements Recyclable {
    public static final int CREATE_NOTE_BEFORE_MSEC = 4000;
    private static final float NOTE_WIDTH = 1.0f / 7.0f;
    private static final float NOTE_HEIGHT = NOTE_WIDTH / 3;
    public static final float NOTE_Y_CREATE = -NOTE_WIDTH;
    public static final float NOTE_Y_HIT_MARGIN = NOTE_WIDTH;
    private Song.Note note;
    private float speed;

    public static NoteSprite get(Song.Note note, float time) {
        NoteSprite ns = (NoteSprite) RecycleBin.get(NoteSprite.class);
        if (ns == null) {
            ns = new NoteSprite();
        }
        ns.init(note, time);
        return ns;
    }

    private void init(Song.Note note, float time) {
        this.note = note;
        float x = (0.5f - 2 * NOTE_WIDTH) * Metrics.width;
        x += NOTE_WIDTH * Metrics.width * note.lane;

        float seconds = note.msec / 1000.0f - time;
        float y = (1.0f - NOTE_Y_HIT_MARGIN) * Metrics.height;
        y -= seconds * Metrics.height * 1000 / CREATE_NOTE_BEFORE_MSEC;

        setCenter(x, y);
    }

    private void setCenter(float x, float y) {
        this.x = x;
        this.y = y;
        float w = dstRect.width();
        float h = dstRect.height();
        setDstRect(w, h);
    }

    private NoteSprite() {
        super(0, 0, Metrics.width * NOTE_WIDTH, Metrics.width * NOTE_HEIGHT, R.mipmap.note_1);
        speed = Metrics.height * 1000 / CREATE_NOTE_BEFORE_MSEC;
        //Log.d("NoteSprite", "speed=" + speed);
    }

    @Override
    public void update(float frameTime) {
        super.update(frameTime);
        setCenter(x, y + speed * frameTime);
        MainScene scene = MainScene.get();
        if (dstRect.top > Metrics.height) {
            scene.remove(this);
        }
    }

    @Override
    public void finish() {

    }
}
