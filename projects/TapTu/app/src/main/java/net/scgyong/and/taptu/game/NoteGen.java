package net.scgyong.and.taptu.game;

import android.graphics.Canvas;
import android.media.MediaPlayer;

import kr.ac.kpu.game.framework.interfaces.GameObject;

public class NoteGen implements GameObject {
    private final MediaPlayer mediaPlayer;
    private float maxTime;
    private Song song;
    public NoteGen(Song song, MediaPlayer mediaPlayer) {
        this.song = song;
        this.mediaPlayer = mediaPlayer;
        maxTime = mediaPlayer.getDuration() / 1000.0f + 5.0f;
    }

    @Override
    public void update(float frameTime) {
        MainScene scene = MainScene.get();

        float time = mediaPlayer.getCurrentPosition() / 1000.0f;
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
