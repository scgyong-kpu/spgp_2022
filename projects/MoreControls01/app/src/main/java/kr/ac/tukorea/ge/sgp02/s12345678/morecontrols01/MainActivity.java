package kr.ac.tukorea.ge.sgp02.s12345678.morecontrols01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnDoIt(View view) {
        CheckBox cb = findViewById(R.id.checkBox);
        Log.d(TAG, "onBtnDoIt(), Checked: " + cb.isChecked());

        EditText edit = findViewById(R.id.nameEdit);
        String text = edit.getText().toString();
        TextView output = findViewById(R.id.outputTextView);
        output.setText(text);
    }

    public void onCheckbox(View view) {
        CheckBox cb = (CheckBox) view;
        Log.d(TAG, "Checked: " + cb.isChecked());
    }
}