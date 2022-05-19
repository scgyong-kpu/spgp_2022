package net.scgyong.and.smoothingpath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private PathView pathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pathView = findViewById(R.id.pathView);
    }

    public void onBtnClear(View view) {
        pathView.clear();
    }
}