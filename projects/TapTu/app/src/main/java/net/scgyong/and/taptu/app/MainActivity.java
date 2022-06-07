package net.scgyong.and.taptu.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import net.scgyong.and.taptu.R;
import net.scgyong.and.taptu.game.MainScene;
import net.scgyong.and.taptu.game.Song;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadSongs();
    }

    ArrayList<Song> songs = new ArrayList<>();
    private void loadSongs() {
        AssetManager assets = getAssets();
        try {
            InputStream is = assets.open("songs.json");
            JsonReader reader = new JsonReader(new InputStreamReader(is));
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                Song song = new Song(reader);
                reader.endObject();
                songs.add(song);
            }
            reader.endArray();
            Log.d(TAG, "Loaded " + songs.size() + " songs");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBtnStartFirst(View view) {
        start("songs/stage_01.txt");
    }

    private void start(String fileName) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(MainScene.PARAM_SONG_FILENAME, fileName);
        startActivity(intent);
    }

    public void onBtnStartSecond(View view) {
        start("songs/stage_02.txt");
    }
}