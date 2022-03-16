package kr.ac.tukorea.ge.sgp02.s12345678.imageswitcher01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnPrev(View view) {
        Log.d(TAG, "Prev Button Pressed");
    }
    public void onBtnNext(View view) {
        Log.d(TAG, "Next Button Pressed");
    }
}