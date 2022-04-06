package kr.ac.tukorea.ge.sgp02.s12345678.samplegame02;

import android.content.res.Resources;

public class Metrics {
    public static float size(int dimenResId) {
        Resources res = GameView.view.getResources();
        return res.getDimension(R.dimen.fighter_radius);
    }
}
