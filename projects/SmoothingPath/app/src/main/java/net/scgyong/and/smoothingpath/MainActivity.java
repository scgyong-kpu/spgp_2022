package net.scgyong.and.smoothingpath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private PathView pathView;
    TextView countTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pathView = findViewById(R.id.pathView);
        countTextView = findViewById(R.id.countTextView);
        pathView.setListener(new PathView.Listener() {
            @Override
            public void onAdd() {
                showCount();
            }
        });
        showCount();
    }

    private void showCount() {
        countTextView.setText("Count: " + pathView.getCount());
    }

    public void onBtnClear(View view) {
        pathView.clear();
        showCount();
    }

    public void onBtnStart(View view) {
        pathView.start();
    }
}