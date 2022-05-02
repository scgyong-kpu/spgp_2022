package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.GameView;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.game.MainGame;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnStart(View view) {
//        Log.d("tag", "onBtnStart()");
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}