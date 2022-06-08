package net.scgyong.and.taptu.game;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.JsonReader;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import kr.ac.kpu.game.framework.view.GameView;

public class Song {
    private static final String TAG = Song.class.getSimpleName();

    public String toJson() {
        return "{" +
                "\"file\": \"" + mp3File + "\"," +
                "\"title\": \"" + title + "\"," +
                "\"artist\": \"" + artist + "\"," +
                "\"albumArt\": \"" + albumFile + "\"," +
                "\"note\": \"" + noteFile + "\"" +
                "}";
    }

    public MediaPlayer loadMusic(AssetManager assets) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor afd = assets.openFd(mp3File);
            mediaPlayer.setDataSource(afd);
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }

    class Note {
        int lane;
        int msec;
        Note(String line) {
            String[] comps = line.split("\\s+");

            lane = Integer.parseInt(comps[1]);
            msec = Integer.parseInt(comps[2]);
        }
    }
    public String mp3File;
    public String noteFile;
    public String title;
    public String artist;
    public String albumFile;
    public Bitmap albumBitmap;

    private ArrayList<Note> notes = new ArrayList<>();
    private int current;
    private long startedOn;

    private float length;
    public float getLength() {
        return length;
    }

    public Song(JsonReader reader, AssetManager assets) throws IOException {
        while (reader.hasNext()) {
            String name = reader.nextName();
            //Log.d(TAG, "Reading name: " + name);
            if (name.equals("file")) {
                mp3File = reader.nextString();
            } else if (name.equals("title")) {
                title = reader.nextString();
            } else if (name.equals("artist")) {
                artist = reader.nextString();
            } else if (name.equals("albumArt")) {
                albumFile = reader.nextString();
                albumBitmap = BitmapFactory.decodeStream(assets.open(albumFile));
            } else if (name.equals("note")) {
                noteFile = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
    }

    public boolean loadNote(AssetManager assets) {
        try {
            InputStream is = assets.open(noteFile);
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
            is.close();
            length = msec / 1000.0f;
            Log.d(TAG, "Title: " + title);
            Log.d(TAG, "Notes loaded: " + notes.size());
            start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
