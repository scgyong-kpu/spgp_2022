package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.GameView;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.game.MainGame;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this, null));
    }

    @Override
    protected void onPause() {
        if (GameView.view != null) {
            GameView.view.pauseGame();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GameView.view != null) {
            GameView.view.resumeGame();
        }
    }

    @Override
    protected void onDestroy() {
        GameView.view = null;
        MainGame.clear();
        super.onDestroy();
    }
}

