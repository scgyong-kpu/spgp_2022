package kr.ac.tukorea.ge.sgp02.s12345678.sample02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.subText);
        tv.setText("I am a good programmer");

    }

    public void onBtnPushMe(View view) {
        TextView tv = findViewById(R.id.subText);
        tv.setText("Clicked !!!");
    }
}