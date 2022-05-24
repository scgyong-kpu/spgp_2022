package kr.ac.kpu.game.s1234567.mapgame.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.ac.kpu.game.s1234567.mapgame.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnStart(View view) {
        startActivity(new Intent(this, GameActivity.class));
    }
}