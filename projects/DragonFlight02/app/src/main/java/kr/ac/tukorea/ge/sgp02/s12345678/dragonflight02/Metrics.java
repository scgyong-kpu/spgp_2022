package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02;

import android.content.res.Resources;
import android.os.Build;
import android.util.TypedValue;

public class Metrics {
    public static int width;
    public static int height;

    public static float size(int dimenResId) {
        Resources res = GameView.view.getResources();
        return res.getDimension(dimenResId);
    }

    public static float floatValue(int dimenResId) {
        Resources res = GameView.view.getResources();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            return res.getFloat(dimenResId);
//        }

        TypedValue outValue = new TypedValue();
        res.getValue(dimenResId, outValue, true);
        float value = outValue.getFloat();
        return value;
    }
}
