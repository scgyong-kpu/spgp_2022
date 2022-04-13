package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework;

import android.content.res.Resources;
import android.util.TypedValue;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.GameView;

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
