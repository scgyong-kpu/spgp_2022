package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.game;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.Sprite;

public class Background extends Sprite {
    public Background(int bitmapResId) {
        super(Metrics.width / 2, Metrics.height / 2,
                Metrics.width, Metrics.height, bitmapResId);
        float height = bitmap.getHeight() * Metrics.width / bitmap.getWidth();
        setDstRect(Metrics.width, height);
    }}
