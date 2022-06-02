package net.scgyong.and.taptu.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import net.scgyong.and.taptu.game.MainScene;

import kr.ac.kpu.game.framework.game.Scene;
import kr.ac.kpu.game.framework.view.GameView;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
//        int stageIndex = intent.getExtras().getInt(MainScene.PARAM_STAGE_INDEX);

        setContentView(new GameView(this, null));

        MainScene game = MainScene.get();
//        game.setMapIndex(stageIndex);
        Scene.push(game);
//        Scene.push(PausedScene.get());
    }

    @Override
    protected void onPause() {
        GameView.view.pauseGame();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GameView.view.resumeGame();
    }

    @Override
    protected void onDestroy() {
        GameView.view = null;
        Scene.clear();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (GameView.view.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}