package kr.ac.tukorea.ge.sgp02.s12345678.cookierun.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.framework.GameView;
import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.game.MainGame;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this, null));
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
        MainGame.clear();
        super.onDestroy();
    }
}