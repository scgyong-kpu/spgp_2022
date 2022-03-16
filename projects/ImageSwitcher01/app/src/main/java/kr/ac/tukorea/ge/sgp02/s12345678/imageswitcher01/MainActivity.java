package kr.ac.tukorea.ge.sgp02.s12345678.imageswitcher01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    protected static final int[] RES_IDS = new int[] {
            R.mipmap.cat_1,
            R.mipmap.cat_2,
            R.mipmap.cat_3,
            R.mipmap.cat_4,
            R.mipmap.cat_5,
    };
    protected int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPage(1);
    }

    public void onBtnPrev(View view) {
        Log.d(TAG, "Prev Button Pressed");
        setPage(page - 1);
    }
    public void onBtnNext(View view) {
        Log.d(TAG, "Next Button Pressed");
        setPage(page + 1);
    }

    private void setPage(int newPage) {
        if (newPage < 1 || newPage > RES_IDS.length) {
            return;
        }
        page = newPage;
        String text = page + " / " + RES_IDS.length;
        TextView tv = findViewById(R.id.pageText);
        tv.setText(text);

        ImageView iv = findViewById(R.id.contentImageView);
        iv.setImageResource(RES_IDS[page - 1]);
    }
}