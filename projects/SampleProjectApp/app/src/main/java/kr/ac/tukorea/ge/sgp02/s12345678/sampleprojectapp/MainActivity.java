package kr.ac.tukorea.ge.sgp02.s12345678.sampleprojectapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.nameText);
        tv.setText("Program started");

        Button btn = findViewById(R.id.btnPushMe);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView tv = findViewById(R.id.nameText);
        tv.setText("Buton Clicked");
    }
}
