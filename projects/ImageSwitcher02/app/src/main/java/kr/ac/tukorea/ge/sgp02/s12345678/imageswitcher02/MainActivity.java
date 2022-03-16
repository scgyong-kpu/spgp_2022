package kr.ac.tukorea.ge.sgp02.s12345678.imageswitcher02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private int pageNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPage(1);
    }

    public void onBtnPrev(View view) {
        Log.d(TAG, "Prev pressed");
        setPage(pageNumber - 1);
    }
    public void onBtnNext(View view) {
        Log.d(TAG, "Next pressed");
        setPage(pageNumber + 1);
    }

    private void setPage(int page) {
        if (page < 1 || page > 5) {
            return;
        }
        pageNumber = page;
        TextView pageTextView = findViewById(R.id.pageTextView);
        String text = pageNumber + " / " + 5;
        pageTextView.setText(text);
    }
}