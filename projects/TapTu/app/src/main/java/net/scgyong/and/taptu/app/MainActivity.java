package net.scgyong.and.taptu.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.scgyong.and.taptu.R;
import net.scgyong.and.taptu.game.MainScene;
import net.scgyong.and.taptu.game.Song;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadSongs();
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClick);
    }

    class Holder {
        TextView title;
        TextView artist;
        ImageView thumbnail;
        Holder(View view) {
            title = view.findViewById(R.id.title);
            artist = view.findViewById(R.id.artist);
            thumbnail = view.findViewById(R.id.thumbnail);
        }
    }

    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return songs.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Song song = songs.get(i);
            LinearLayout layout = (LinearLayout) view;
            Holder holder;
            if (layout == null) {
                layout = (LinearLayout) getLayoutInflater().inflate(R.layout.song_item, null);
                holder = new Holder(layout);
                layout.setTag(holder);
            } else {
                holder = (Holder) layout.getTag();
            }
            holder.title.setText(song.title);
            holder.artist.setText(song.artist);
            holder.thumbnail.setImageBitmap(song.albumBitmap);

            return layout;
        }
    };

    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            view.setSelected(true);
            selectedPosition = position;
        }
    };

    ArrayList<Song> songs = new ArrayList<>();
    private void loadSongs() {
        AssetManager assets = getAssets();
        try {
            InputStream is = assets.open("songs.json");
            JsonReader reader = new JsonReader(new InputStreamReader(is));
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                Song song = new Song(reader, assets);
                reader.endObject();
                Log.d(TAG, "Bitmap #" + songs.size() + " = " + song.albumBitmap);
                songs.add(song);
            }
            reader.endArray();
            Log.d(TAG, "Loaded " + songs.size() + " songs");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBtnStart(View view) {
//        int pos = listView.getSelectedItemPosition();
        Log.d(TAG, "Selected Position = " + selectedPosition);
        if (selectedPosition < 0) return;

        Song song = songs.get(selectedPosition);
        String json = song.toJson();
        Log.d(TAG, "JSON: " + json);
    }

    private void start(String fileName) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(MainScene.PARAM_SONG_FILENAME, fileName);
        startActivity(intent);
    }
}
