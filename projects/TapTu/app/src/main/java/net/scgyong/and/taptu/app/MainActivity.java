package net.scgyong.and.taptu.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.scgyong.and.taptu.R;
import net.scgyong.and.taptu.game.MainScene;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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