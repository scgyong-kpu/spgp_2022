package kr.ac.tukorea.ge.sgp02.s12345678.cards01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int[] BUTTON_IDS = new int[] {
            R.id.card_00,R.id.card_01,R.id.card_02,R.id.card_03,
            R.id.card_10,R.id.card_11,R.id.card_12,R.id.card_13,
            R.id.card_20,R.id.card_21,R.id.card_22,R.id.card_23,
            R.id.card_30,R.id.card_31,R.id.card_32,R.id.card_33,
    };
    private int[] resIds = new int[] {
            R.mipmap.card_as,R.mipmap.card_2c,R.mipmap.card_3d,R.mipmap.card_4h,
            R.mipmap.card_5s,R.mipmap.card_jc,R.mipmap.card_qh,R.mipmap.card_kd,
            R.mipmap.card_as,R.mipmap.card_2c,R.mipmap.card_3d,R.mipmap.card_4h,
            R.mipmap.card_5s,R.mipmap.card_jc,R.mipmap.card_qh,R.mipmap.card_kd,
    };

    private ImageButton previousImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGame();
    }

    private void startGame() {
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            ImageButton btn = findViewById(BUTTON_IDS[i]);
            int resId = resIds[i];
//            btn.setImageResource(resId);
            btn.setTag(resId);
        }
    }

    public void onBtnRestart(View view) {
        Log.d(TAG, "Restart");
    }
    public void onBtnCard(View view) {
        if (!(view instanceof ImageButton)) {
            Log.e(TAG, "Not an ImageButton: " + view);
            return;
        }
        ImageButton imageButton = (ImageButton) view;
        int cardIndex = findButtonIndex(imageButton.getId());
        Log.d(TAG, "Card Index: " + cardIndex);

        if (previousImageButton == imageButton) {
            Log.d(TAG, "Same image button");
            return;
        }

        int resId = (Integer) imageButton.getTag();
        imageButton.setImageResource(resId);
        if (previousImageButton != null) {
            previousImageButton.setImageResource(R.mipmap.card_blue_back);
        }

        previousImageButton = imageButton;
    }

    private int findButtonIndex(int id) {
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            if (id == BUTTON_IDS[i]) {
                return i;
            }
        }
        return -1;
    }
}