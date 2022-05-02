package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight02.game;

import android.graphics.Canvas;

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
//        if (y > Metrics.height) y = 0;
//        setDstRect(Metrics.width, height);
    }

    @Override
    public void draw(Canvas canvas) {
//        super.draw(canvas);
        int curr = (int)y % Metrics.height;
        if (curr > 0) curr -= height;
        while (curr < Metrics.height) {
            dstRect.set(0, curr, Metrics.width, curr + height);
            canvas.drawBitmap(bitmap, null, dstRect, null);
            curr += height;
        }
    }
}
