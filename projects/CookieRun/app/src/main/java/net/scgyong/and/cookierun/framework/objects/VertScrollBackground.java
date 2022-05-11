package net.scgyong.and.cookierun.framework.objects;

import android.graphics.Canvas;

import net.scgyong.and.cookierun.framework.game.BaseGame;
import net.scgyong.and.cookierun.framework.res.Metrics;

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
    public void update(float frameTime) {
        this.y += speed * frameTime;
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
