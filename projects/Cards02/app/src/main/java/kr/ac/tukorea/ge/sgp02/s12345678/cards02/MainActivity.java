package kr.ac.tukorea.ge.sgp02.s12345678.cards02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnRestart(View view) {
        Log.d(TAG, "onBtnRestart");
    }
    public void onBtnCard(View view) {
        Log.d(TAG, "onBtnCard: " + view.getId());
    }
}