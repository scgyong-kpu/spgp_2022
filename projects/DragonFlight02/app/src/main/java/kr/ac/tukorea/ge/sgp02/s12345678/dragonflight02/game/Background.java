package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.game;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.framework.Sprite;

public class Background extends Sprite {
    private final float speed;
    private final int height;

    public Background(int bitmapResId, float speed) {
        super(Metrics.width / 2, Metrics.height / 2,
                Metrics.width, Metrics.height, bitmapResId);
        height = bitmap.getHeight() * Metrics.width / bitmap.getWidth();
        setDstRect(Metrics.width, height);

        this.speed = speed;
    }

    @Override
    public void update() {
        this.y += speed * MainGame.getInstance().frameTime;
        setDstRect(Metrics.width, height);
    }
}
