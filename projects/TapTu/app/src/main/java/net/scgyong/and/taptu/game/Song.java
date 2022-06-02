package net.scgyong.and.taptu.game;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import kr.ac.kpu.game.framework.view.GameView;

public class Song {
    private static final String TAG = Song.class.getSimpleName();

    class Note {
        int lane;
        int msec;
        Note(String line) {
            String[] comps = line.split("\\s+");

            lane = Integer.parseInt(comps[1]);
            msec = Integer.parseInt(comps[2]);
        }
    }
    private String title;
    private ArrayList<Note> notes = new ArrayList<>();
    private int current;
    private long startedOn;

    private float length;
    public float getLength() {
        return length;
    }

    public Song(String fileName) {
        Context context = GameView.view.getContext();
        AssetManager assets = context.getAssets();
        try {
            InputStream is = assets.open(fileName);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            int msec = 0;
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                if (line.startsWith("T ")) {
                    title = line.substring(2).trim();
                } else if (line.startsWith("N")) {
                    Note note = new Note(line);
                    notes.add(note);
                    if (msec < note.msec) {
                        msec = note.msec;
                    }
                }
            }
            length = msec / 1000.0f;
            Log.d(TAG, "Title: " + title);
            Log.d(TAG, "Notes loaded: " + notes.size());
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isValid() {
        if (title == null) return false;
        if (notes.size() == 0) return false;
        return true;
    }

    public void start() {
        current = 0;
        startedOn = System.currentTimeMillis();
    }

    public Note getNextNote(int msec) {
        if (current >= notes.size()) {
            return null;
        }
        int elapsed = (int) (System.currentTimeMillis() - startedOn);
        Note note = notes.get(current);
        if (note.msec > elapsed + msec) {
            return null;
        }
        current++;
        return note;
    }
}
