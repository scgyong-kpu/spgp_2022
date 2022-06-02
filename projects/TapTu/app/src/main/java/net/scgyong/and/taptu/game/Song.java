package net.scgyong.and.taptu.game;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
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
            String[] comps = line.split("/\\s+");

            lane = Integer.parseInt(comps[1]);
            msec = Integer.parseInt(comps[2]);
        }
    }
    private String title;
    private ArrayList<Note> notes = new ArrayList<>();

    public Song(String fileName) {
        Context context = GameView.view.getContext();
        AssetManager assets = context.getAssets();
        try {
            InputStream is = assets.open(fileName);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
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
                }
            }
            Log.d(TAG, "Title: " + title);
            Log.d(TAG, "Notes loaded: " + notes.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isValid() {
        if (title == null) return false;
        if (notes.size() == 0) return false;
        return true;
    }
}
