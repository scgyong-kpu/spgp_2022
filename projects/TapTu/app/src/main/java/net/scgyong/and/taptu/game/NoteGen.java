package net.scgyong.and.taptu.game;

import android.graphics.Canvas;

import kr.ac.kpu.game.framework.interfaces.GameObject;

public class NoteGen implements GameObject {
    private float time = 0, maxTime;
    private Song song;
    public NoteGen(Song song) {
        this.song = song;
        maxTime = song.getLength() + 5.0f;
    }

    public float getTime() {
        return time;
    }

    @Override
    public void update(float frameTime) {
        MainScene scene = MainScene.get();

        time += frameTime;
        if (time > maxTime) {
            scene.finish();
            return;
        }
        while (true) {
            Song.Note note = song.getNextNote(NoteSprite.CREATE_NOTE_BEFORE_MSEC);
            if (note == null) break;
            NoteSprite ns = NoteSprite.get(note, time);
            scene.add(MainScene.Layer.note.ordinal(), ns);
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
