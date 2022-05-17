package net.scgyong.and.cookierun.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import net.scgyong.and.cookierun.framework.game.Scene;
import net.scgyong.and.cookierun.framework.view.GameView;
import net.scgyong.and.cookierun.game.MainScene;
import net.scgyong.and.cookierun.game.PausedScene;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int stageIndex = intent.getExtras().getInt(MainScene.PARAM_STAGE_INDEX);

        setContentView(new GameView(this, null));

        MainScene game = MainScene.get();
        game.setMapIndex(stageIndex);
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