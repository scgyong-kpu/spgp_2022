package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight;

import android.content.res.Resources;

public class Metrics {
    public static int width;
    public static int height;

    public static float size(int dimenResId) {
        Resources res = GameView.view.getResources();
        float size = res.getDimension(dimenResId);
        return size;
    }
}
