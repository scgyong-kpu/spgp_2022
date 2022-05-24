package kr.ac.kpu.game.s1234567.mapgame.framework.objects;

import android.graphics.Canvas;

import kr.ac.kpu.game.s1234567.mapgame.framework.res.Metrics;

public class HorzScrollBackground extends Sprite {
    private final float speed;
    private final int width;
    public HorzScrollBackground(int bitmapResId, float speed) {
        super(Metrics.width / 2, Metrics.height / 2,
                Metrics.width, Metrics.height, bitmapResId);
        this.width = bitmap.getWidth() * Metrics.height / bitmap.getHeight();
        setDstRect(width, Metrics.height);
        this.speed = speed;
    }

    @Override
    public void update(float frameTime) {
        this.x += speed * frameTime;
    }

    @Override
    public void draw(Canvas canvas) {
        int curr = (int)x % width;
        if (curr > 0) curr -= width;
        while (curr < Metrics.width) {
            dstRect.set(curr, 0, curr + width, Metrics.height);
            canvas.drawBitmap(bitmap, null, dstRect, null);
            curr += width;
        }
    }
}
