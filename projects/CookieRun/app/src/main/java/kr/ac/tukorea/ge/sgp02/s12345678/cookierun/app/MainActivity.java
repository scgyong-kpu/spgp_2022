package kr.ac.tukorea.ge.sgp02.s12345678.cookierun.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.ac.tukorea.ge.sgp02.s12345678.cookierun.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnPlay(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}