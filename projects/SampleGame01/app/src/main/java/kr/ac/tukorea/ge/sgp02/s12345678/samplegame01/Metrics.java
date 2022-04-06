package kr.ac.tukorea.ge.sgp02.s12345678.samplegame01;

import android.content.res.Resources;

public class Metrics {
    public static float size(int dimenResId) {
        Resources res = GameView.view.getResources();
        float size = res.getDimension(dimenResId);
        return size;
    }
}
