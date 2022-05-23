package kr.ac.tukorea.ge.sgp02.s12345678.smoothingpath02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private PathView pathView;
    private TextView countTextView;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countTextView = findViewById(R.id.countTextView);
        pathView = findViewById(R.id.pathView);
        seekBar = findViewById(R.id.seekBar);
        pathView.setListener(new PathView.Listener() {
            @Override
            public void onAdd() {
                showCount();
            }
        });
        showCount();
    }

    private void showCount() {
        int count = pathView.getPointCount();
        String text = "Count: " + count;
        countTextView.setText(text);
    }

    public void onBtnClear(View view) {
        pathView.clear();
        showCount();
    }

    public void onBtnStart(View view) {
        int msec = 1000 - seekBar.getProgress();
        pathView.start(msec);
    }
}