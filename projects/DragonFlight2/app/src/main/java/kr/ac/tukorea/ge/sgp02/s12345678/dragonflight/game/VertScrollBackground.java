package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.BaseGame;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Sprite;

public class VertScrollBackground extends Sprite {
    private final float speed;
    private final int height;
    public VertScrollBackground(int bitmapResId, float speed) {
        super(Metrics.width / 2, Metrics.height / 2,
                Metrics.width, Metrics.height, bitmapResId);
        this.height = bitmap.getHeight() * Metrics.width / bitmap.getWidth();
        setDstRect(Metrics.width, height);
        this.speed = speed;
    }

    @Override
    public void update() {
        this.y += speed * BaseGame.getInstance().frameTime;
//        if (y > Metrics.height) {
//            y = 0;
//        }
//        setDstRect(Metrics.width, height);
    }

    @Override
    public void draw(Canvas canvas) {
        int curr = (int)y % height;
        if (curr > 0) curr -= height;
        while (curr < Metrics.height) {
            dstRect.set(0, curr, Metrics.width, curr + height);
            canvas.drawBitmap(bitmap, null, dstRect, null);
            curr += height;
        }
    }
}
