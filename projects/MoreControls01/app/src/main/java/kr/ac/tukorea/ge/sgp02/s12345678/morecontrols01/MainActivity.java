package kr.ac.tukorea.ge.sgp02.s12345678.morecontrols01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText edit;
    private TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = findViewById(R.id.nameEdit);
        output = findViewById(R.id.outputTextView);


        edit.addTextChangedListener(editWatcher);
    }

    public void onBtnDoIt(View view) {
        CheckBox cb = findViewById(R.id.checkBox);
        Log.d(TAG, "onBtnDoIt(), Checked: " + cb.isChecked());

        String text = edit.getText().toString();
        output.setText(text);
    }

    public void onCheckbox(View view) {
        CheckBox cb = (CheckBox) view;
        Log.d(TAG, "Checked: " + cb.isChecked());
    }

    TextWatcher editWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.v(TAG, "before");
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.d(TAG, "textChange: " + charSequence);
            output.setText("Text Length: " + charSequence.length());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            Log.v(TAG, "after");
        }
    };
}