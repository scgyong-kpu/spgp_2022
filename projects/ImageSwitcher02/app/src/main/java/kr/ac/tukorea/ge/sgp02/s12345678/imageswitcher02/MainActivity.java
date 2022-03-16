package kr.ac.tukorea.ge.sgp02.s12345678.imageswitcher02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnPrev(View view) {
        Log.d("MainActivity", "Prev pressed");
    }
    public void onBtnNext(View view) {
        Log.d("MainActivity", "Next pressed");
    }
}